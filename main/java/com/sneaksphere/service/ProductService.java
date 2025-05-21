package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;
//Standard java classes for SQL database connections and queries
import java.sql.*;
//Arratlist and list used for handling the list of sneakers.
import java.util.ArrayList;
import java.util.List;

public class ProductService {
	/*This line defines a constant default limit which will be used as the default limit for
	 * the number of products to fetch(used in getNewReleases method)
	 */
    private static final int DEFAULT_LIMIT = 6;

 
    
    /**
     * Fetches products by category with default sorting (newest first)
     * @param category The category to filter by
     * @return List of SneakerModel objects, or empty list if error occurs
     */
    public List<SneakerModel> getProductsByCategory(String category) {
        // Default to featured sorting
        return getProductsByCategory(category, "featured");
    }

    /**
     * Fetches products by category with specified sorting
     * @param category The category to filter by
     * @param sortOption The sorting preference (price-low, price-high, etc.)
     * @return List of SneakerModel objects, or empty list if error occurs
     */
    //This method fetches product based on both category and sorting preferences
    public List<SneakerModel> getProductsByCategory(String category, String sortOption) {
    	/*Query selects sneakers data from the database where product is available 
    	 and the products category matches the provides category
    	 */
        String sql = "SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL " +
                     "FROM sneaker WHERE AvailabilityStatus = 'Available' " +
                     "AND Category = ? ";
        
        /*This modifies the SQL query based on the sorting option.
         *
         */
        /*Ternary conditional operator is used if sorting option is not null, it uses the value of sorting option
         * if null then it uses default to the value featured
         */
        switch(sortOption != null ? sortOption : "featured") {
            case "price-low":
                sql += "ORDER BY Price ASC";
                break;
            case "price-high":
                sql += "ORDER BY Price DESC";
                break;
            case "alpha-az":
                sql += "ORDER BY SneakerName ASC";
                break;
            case "brand-az":
                sql += "ORDER BY Brand ASC";
                break;
            default: // "featured" or any other case
                sql += "ORDER BY ReleasedDate DESC";
        }
        
        //connection object (conn) is used to interact with the database
        try (Connection conn = Dbconfig.getDbConnection();
        /*
         * It is used to execute the SQL query that is stored in the sql variable. 
         * The PreparedStatement is typically used to prevent SQL injection by allowing parameters 
         to be set dynamically in the query.
         */
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	//sets the value of the category variable into the SQL query where the first ? placeholder is.
            stmt.setString(1, category);
            //Executes the query and returns the result as a list of SneakerModel Object
            return executeSneakerQuery(stmt);
         //Catches any database related or class not found errors during the executions
        } catch (SQLException | ClassNotFoundException e) {
        	//prints an error message to the console if any exceptions occurs
            System.err.println("[ERROR] getProductsByCategory failed: " + e.getMessage());
            //returns an empty list to avoid null and prevents error in case of an exception
            return new ArrayList<>();
        }
    }

    
    /**
     * Fetches the latest available sneakers (new releases).
     * @param limit Maximum number of products to fetch (defaults to 6 if <= 0).
     * @return List of SneakerModel objects, or empty list if error occurs.
     */
 /*
  Returns a list of SneakerModel objects. The method take one parameter, limit which
  specifies how many new releases (sneakers) to fetch   
  */
    public List<SneakerModel> getNewReleases(int limit) {
   /*
    If limit i greater than 0, querylimit is set to the value of limit.
   If limit is less than or equal to 0, it defaults to the DEFAULT_LIMIT value which is 6
    */
        int queryLimit = (limit > 0) ? limit : DEFAULT_LIMIT;
     /*
      this query will provide the sneakers which are available and sprted by releaseddate in descending order
      */
        String sql = "SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL " +
                     "FROM sneaker WHERE AvailabilityStatus = 'Available' " +
                     "ORDER BY ReleasedDate DESC LIMIT ?";
        //data base connection
        try (Connection conn = Dbconfig.getDbConnection();
        	// stmt is a prepare statement object used to execute the sql query. 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	//set the value for the first placeholder (?) in the SQL query
            stmt.setInt(1, queryLimit);
            //executes the query and maps the result to a list of SneakerModel Object  
            return executeSneakerQuery(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getNewReleases failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Fetches a single sneaker by ID (useful for product details page).
     * @param sneakerID The ID of the sneaker to fetch.
     * @return SneakerModel if found, null otherwise.
     */
    public SneakerModel getSneakerById(int sneakerID) {
        String sql = "SELECT SneakerID, SneakerName, Description, Category, " +
                     "Brand, Price, ImageURL, ReleasedDate, SneakerSize " +  // Added SneakerSize
                     "FROM sneaker WHERE SneakerID = ?";

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sneakerID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                SneakerModel sneaker = new SneakerModel();
                sneaker.setSneakerID(rs.getInt("SneakerID"));
                sneaker.setSneakerName(rs.getString("SneakerName"));
                sneaker.setDescription(rs.getString("Description"));
                sneaker.setCategory(rs.getString("Category"));
                sneaker.setBrand(rs.getString("Brand"));
                sneaker.setPrice(rs.getFloat("Price"));
                sneaker.setImageUrl(rs.getString("ImageURL"));
                
                // Add this line to get the size
                sneaker.setSneakerSize(rs.getFloat("SneakerSize"));
                
                if (columnExists(rs, "ReleasedDate")) {
                    sneaker.setReleasedDate(rs.getDate("ReleasedDate").toLocalDate());
                }
                return sneaker;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getSneakerById failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches products by multiple categories.
     * @param categories The categories to filter by
     * @return List of SneakerModel objects, or empty list if error occurs
     */
    public List<SneakerModel> getProductsByCategories(String sortOption, String... categories) {
    //Check if the categories array is null or empty, and return an empty list if so
        if (categories == null || categories.length == 0) {
            return new ArrayList<>();
        }
        
        /*This is used to construct the SQL query dynamically. its preferred over string because it
         * is more efficient when building large strings.
         */
        StringBuilder sql = new StringBuilder("SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL ")
                .append("FROM sneaker WHERE AvailabilityStatus = 'Available' ")
                .append("AND Category IN (");
        //iterated through each category 
        for (int i = 0; i < categories.length; i++) {
            sql.append("?");
            //add comma between categories but no after last one.
            if (i < categories.length - 1) {
                sql.append(",");
            }
        }
        sql.append(") ");

        // Add sorting based on the option
        switch(sortOption != null ? sortOption : "featured") {
            case "price-low":
                sql.append("ORDER BY Price ASC");
                break;
            case "price-high":
                sql.append("ORDER BY Price DESC");
                break;
            case "alpha-az":
                sql.append("ORDER BY SneakerName ASC");
                break;
            case "brand-az":
                sql.append("ORDER BY Brand ASC");
                break;
            default: // "featured" or any other case
                sql.append("ORDER BY ReleasedDate DESC");
        }

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < categories.length; i++) {
                stmt.setString(i + 1, categories[i]);
            }
            
            return executeSneakerQuery(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getProductsByCategories failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    
    private List<SneakerModel> executeSneakerQuery(PreparedStatement stmt) throws SQLException {
    	//An empty list sneakers is created to store the SneakerModel object that will be fetched from the query results
        List<SneakerModel> sneakers = new ArrayList<>();
        //executes the sql query stored in PreparedStatement object stmt using the executeQuery()method
        try (ResultSet rs = stmt.executeQuery()) {
        	/*For each row, this line calls the mapResultSetToSneaker(rs) method.
        	 * It passes the current row's ResultSet to mapResultSetToSneaker, which maps the data from that row into a SneakerModel object.
        	 * The created SneakerModel object is then added to the sneakers list.
        	 */
            while (rs.next()) {
            //The ResultSet is iterated row by using a loop
            // for each row mapResultSetToSneaker method is called, passing the current ResultSet row to convert it into a SneakerModel Object
                sneakers.add(mapResultSetToSneaker(rs));
            }
        }
        //after processing all the rows, the list of sneakerModel object is returned.
        return sneakers;
    }
    
    
    public List<SneakerModel> getRelatedProducts(String brand, String category, int excludeId, int limit) {
        String sql = "SELECT SneakerID, SneakerName, Category, Brand, SneakerSize, Price, ImageURL " +
                     "FROM sneaker WHERE AvailabilityStatus = 'Available' " +
                     "AND SneakerID != ? AND (Brand = ? OR Category = ?) " +
                     "ORDER BY ReleasedDate DESC LIMIT ?";
        
        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, excludeId);
            stmt.setString(2, brand);
            stmt.setString(3, category);
            stmt.setInt(4, limit);
            
            return executeSneakerQuery(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getRelatedProducts failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Fetches available sizes for a specific sneaker
     * @param sneakerId The ID of the sneaker
     * @return List of available sizes
     */
    public List<String> getAvailableSizes(int sneakerId) {
        List<String> sizes = new ArrayList<>();
        String sql = "SELECT DISTINCT SneakerSize FROM sneaker WHERE SneakerID = ?";
        
        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sneakerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                sizes.add(rs.getString("SneakerSize"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getAvailableSizes failed: " + e.getMessage());
        }
        return sizes;
    }
    
    
    /*This is method used to map a row from the ResultSet into a SneakerModel Object
     * Takes a ResultSet object rs as a parameter and returns a SneakerModel object
     */
    private SneakerModel mapResultSetToSneaker(ResultSet rs) throws SQLException {
    	//a new SneakerModel object sneaker is created to hold the data mapped from the ResultSet.
        SneakerModel sneaker = new SneakerModel();
        /*This line retrieves the value of the SneakerID column from the ResultSet 
         * and sets it in the SneakerModel object using the setSneakerID() method.
         * Same for Others
         */
        sneaker.setSneakerID(rs.getInt("SneakerID"));
        sneaker.setSneakerName(rs.getString("SneakerName"));
        sneaker.setCategory(rs.getString("Category"));
        sneaker.setBrand(rs.getString("Brand"));
        sneaker.setPrice(rs.getFloat("Price"));
        sneaker.setImageUrl(rs.getString("ImageURL"));
        
        if (columnExists(rs, "Description")) {
            sneaker.setDescription(rs.getString("Description"));
        }
        if (columnExists(rs, "ReleasedDate")) {
            sneaker.setReleasedDate(rs.getDate("ReleasedDate").toLocalDate());
        }
        return sneaker;
    }

    
    /*This method is used to check if a column exists in the ResultSet
     * It takes a ResultSet object rs and a columnName string as parameters, 
       and returns a boolean value (true or false).
     */
    private boolean columnExists(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}