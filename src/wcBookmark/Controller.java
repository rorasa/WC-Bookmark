package wcBookmark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Controller {

// Link symbols between java code and fxml file
//    @FXML private ChoiceBox bookmark_type;
//    @FXML private TextField bookmark_name;
//    @FXML private ChoiceBox shop_country;
    @FXML private Label bmName;
    @FXML private Label bmDescription;
    @FXML private Label bmCollection;
    @FXML private Label bmType;
    @FXML private Label bmBoughtFrom;
    @FXML private Label bmBoughtDate;
    @FXML private Label bmDateAdded;
    @FXML private Label bmPrice;
    @FXML private TextArea bmNote;

    @FXML private TextArea expert_in;
	@FXML private TextArea expert_out;
	@FXML private Button expert_submit;

//	@FXML private Button shop_add;
//	@FXML private Button shop_delete;
	@FXML private TableView bookmark_table;

// Declare class variables
	DbManager db;
	ResultSet bookmarkResults;
//	ResultSet shopResults;

    public void initialize(){

        ChoiceList choices = new ChoiceList();
  //      bookmark_type.setItems(choices.getMaterialList());
  //      bookmark_type.setValue("Leather");

  //      shop_country.setItems(choices.getCountryList());

        bmNote.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldProp, Boolean newProp) {
                if (!newProp){
                    try{
                        bmNoteOnAction();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }); 

		initialiseBookmarkTable();
    }

	public void setDatabase(DbManager db){
		this.db = db;
		populateBookmarkTable();
	}

	public void refresh(){
		populateBookmarkTable();
	}

    @FXML protected void expertSubmitOnAction(ActionEvent event){

        String sql = expert_in.getText();
		String outText = sql + "\n\n";

		try {
			Boolean isResultSet = db.execute(sql);
			if(isResultSet){
				ResultSet rs = db.getStatement().getResultSet();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				for (int i = 1; i <= columnsNumber; i++){
					if (i > 1){outText += " | ";}
					outText += rsmd.getColumnName(i);
				}
				outText += "\n";
				while (rs.next()){
					for (int i = 1; i <= columnsNumber; i++) {
						if (i > 1){outText += " | ";}
						outText += rs.getString(i);
					}
					outText += "\n";
				}
				outText += "\nOperation finished successfully: "+db.getStatement().getUpdateCount()+" updated.";
			}else{
				outText += "Operation finished successfully: "+db.getStatement().getUpdateCount()+" updated.";
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		expert_out.setText(outText);

		expert_in.setText("");
	}

//	@FXML protected void shopAddOnAction(ActionEvent event) throws Exception{
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("shop_add.fxml"));
//		Parent root = loader.load();
//		ShopAddController controller = loader.getController();
//		controller.setDatabase(db);
//		controller.setParent(this);
//
//		Stage stage = new Stage();
//		stage.setTitle("Add new shop");
//		stage.setScene(new Scene(root, 1000, 800));
//		stage.show();
//	}

//	@FXML protected void shopDeleteOnAction(ActionEvent event){
//		int index = shop_table.getSelectionModel().selectedIndexProperty().get();
//		System.out.println(index);
//	}

	private void initialiseBookmarkTable(){
        // create columes
		TableColumn id = new TableColumn("ID");
		TableColumn name = new TableColumn("Name");
		TableColumn description = new TableColumn("Description");

		bookmark_table.getColumns().addAll(id, name, description);

        // create CellFactory to manage the population of each column
		id.setCellValueFactory(new PropertyValueFactory<bookmarkEntry, String>("id"));
		name.setCellValueFactory(new PropertyValueFactory<bookmarkEntry, String>("name"));
		description.setCellValueFactory(new PropertyValueFactory<bookmarkEntry, String>("description"));

		bookmark_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					bookmarkTableOnAction(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    private void populateBookmarkTable(){
        String sql = "SELECT * FROM BOOKMARK";
        ResultSet rs = db.executeQuery(sql);
        bookmarkResults = rs;

        ObservableList<bookmarkEntry> data = FXCollections.observableArrayList();
        try{
            while(rs.next()){
                data.add(new bookmarkEntry(rs.getInt("ID"), rs.getString("NAME"), rs.getString("DESCRIPTION")));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        bookmark_table.setItems(data);
    }
//	private void populateShopTable(){
//		String sql = "SELECT * FROM SHOP";
//		ResultSet rs = db.executeQuery(sql);
//		shopResults = rs;
//
//		ObservableList<shopItem> data = FXCollections.observableArrayList();
//		try {
//			while(rs.next()){
 //               data.add(new shopItem(rs.getInt("ID"), rs.getString("NAME"), rs.getString("LOCATION"), rs.getString("//COUNTRY")));
   //         }
	//	} catch (SQLException e) {
	//		e.printStackTrace();
	//	}
//
//		shop_table.setItems(data);
//	}

    private void bookmarkTableOnAction(MouseEvent event) throws Exception{
        int index = bookmark_table.getSelectionModel().selectedIndexProperty().get();
        System.out.println(index);

        bookmarkResults.absolute(index+1);
        System.out.println(bookmarkResults.getString("NAME")+" "+bookmarkResults.getString("DESCRIPTION")+" "+bookmarkResults.getString("TYPE")+" price: "+bookmarkResults.getString("PRICE")+" "+bookmarkResults.getString("BOUGHT_FROM"));

        // read values from result sets
        String name = bookmarkResults.getString("NAME");
        String description = bookmarkResults.getString("DESCRIPTION");
        String collection = bookmarkResults.getString("COLLECTION_ID");
        String type = bookmarkResults.getString("TYPE");
        String price = bookmarkResults.getString("PRICE");
        String bought_from = bookmarkResults.getString("BOUGHT_FROM");
        String bought_date = bookmarkResults.getString("BOUGHT_DATE");
        String note = bookmarkResults.getString("NOTE");
        
        // replace all null value with n/a
        if(name==null){ name = "Untitled"; }
        if(description==null){ description = ""; }
        if(collection==null){ collection = "n/a"; }
        if(type==null){ type = "n/a"; }
        if(price==null){ price = "n/a"; }
        if(bought_from==null){ bought_from = "n/a"; }
        if(bought_date==null){ bought_date = "n/a"; }
        if(note==null){ note = ""; }

        bmName.setText(name);
        bmDescription.setText(description);
        bmCollection.setText("Collection ID: "+collection);
        bmType.setText("Type: "+type);
        bmBoughtFrom.setText("Place of purchase: "+bought_from);
        bmBoughtDate.setText("Date of purchase: "+bought_date);
        bmPrice.setText("Initial price: "+price);
        bmNote.setText(note);
    }

    private void bmNoteOnAction() throws Exception{
        int index = bookmark_table.getSelectionModel().selectedIndexProperty().get(); 
        if(index<0){ return; }
        bookmarkResults.absolute(index+1);
        int id = bookmarkResults.getInt("ID");

        String note = bmNote.getText();
        String sql = "UPDATE BOOKMARK SET NOTE='"+note+"' WHERE ID="+id;
        
        db.execute(sql);
        refresh();
    }

//	private void shopTableOnAction(MouseEvent event) throws Exception{
//		int index = shop_table.getSelectionModel().selectedIndexProperty().get();
//		System.out.println(index);
//
//		shopResults.absolute(index+1);
//		System.out.println(shopResults.getString("NAME")+" "+shopResults.getString("LOCATION")+" "+shopResults.getStri//ng("COUNTRY")+
//		shopResults.getString("TEL")+" "+shopResults.getString("EMAIL")+" "+shopResults.getString("WEBSITE"));
//	}
//
    public class bookmarkEntry{
        // template class for each bookmark entry in the bookmark_table.
        // this is an internal class called by the CellFactory.
        private int id;
        private String name;
        private String description;
        
        private bookmarkEntry(int id, String name, String description){
            this.id = id;
            this.name = name;
            this.description = description;
        }
        public int getId(){ return id; }
        public void setId(int id){ this.id = id; }
        public String getName(){ return name; }
        public void setName(String name){ this.name = name; }
        public String getDescription(){ return description; }
        public void setDescription(){ this.description = description; }
    }
//	public class shopItem {
//		private int id;
//		private String name;
//		private String location;
//		private String country;
//
//		private shopItem(int id, String name, String loc, String country){
//			this.id = id;
//			this.name = name;
//			this.location = loc;
//			this.country = country;
//		}
//		public int getId(){	return id; }
//		public void setId(int id){ this.id = id; }
//		public String getName(){ return name; }
//		public void setName(String n){this.name = n; }
//		public String getLocation(){ return location; }
//		public void setLocation(String l){this.location = l; }
//		public String getCountry(){ return country; }
//		public void setCountry(String c){this.country = c; }
//	}

	private class ChoiceList {

		public ObservableList getMaterialList(){
			return FXCollections.observableArrayList("Leather", "Paper", "Metal","Other");
		}

		public ObservableList getCountryList(){
			return FXCollections.observableArrayList(
					"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola","Anguilla",
					"Antarctica","Antigua And Barbuda","Argentina","Armenia","Aruba","Australia","Austria",
					"Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize",
					"Benin","Bermuda","Bhutan","Bolivia","Bosnia And Herzegovina","Botswana","Bouvet Island",
					"Brazil","British Indian Ocean Territory","Brunei Darussalam","Bulgaria","Burkina Faso",
					"Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic",
					"Chad","Chile","China","Christmas Island","Colombia","Comoros",
					"Congo","Congo, Republic Of","Cook Islands","Costa Rica","Cote D'ivoire",
					"Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic",
					"East Timor","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia",
					"Falkland Islands","Faroe Islands","Fiji","Finland","France","French Guiana",
					"French Polynesia","French Southern Territories","Gabon","Gambia","Georgia","Germany",
					"Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea",
					"Guinea-bissau","Guyana","Haiti","Holy See","Honduras",
					"Hong Kong","Hungary","Iceland","India","Indonesia","Iran",
					"Iraq","Ireland","Israel","Italy","Jamaica","Japan",
					"Jordan","Kazakstan","Kenya","Kiribati","Korea, North","Korea, South",
					"Kosovo","Kuwait","Kyrgyzstan","Lao","Latvia","Lebanon",
					"Lesotho","Liberia","Libyan Arab Jamahiriya","Liechtenstein","Lithuania","Luxembourg",
					"Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives",
					"Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius",
					"Mayotte","Mexico","Micronesia","Moldova","Monaco","Mongolia",
					"Montserrat","Montenegro","Morocco","Mozambique","Myanmar","Namibia",
					"Nauru","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand",
					"Nicaragua","Niger","Nigeria","Niue","Norfolk Island","Northern Mariana Islands",
					"Norway","Oman","Pakistan","Palau","Palestinian Territory","Panama",
					"Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn","Poland",
					"Portugal","Puerto Rico","Qatar","Reunion","Romania","Russian Federation",
					"Rwanda","Saint Helena","Saint Kitts And Nevis","Saint Lucia","Saint Pierre And Miquelon",
					"Saint Vincent And The Grenadines",
					"Samoa","San Marino","Sao Tome And Principe","Saudi Arabia","Senegal","Serbia",
					"Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands",
					"Somalia","South Africa","South Georgia","Spain","Sri Lanka","Sudan",
					"Suriname","Svalbard And Jan Mayen","Swaziland","Sweden","Switzerland","Syrian Arab Republic",
					"Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tokelau",
					"Tonga","Trinidad And Tobago","Tunisia","Turkey","Turkmenistan","Turks And Caicos Islands",
					"Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States",
					"United States Minor Outlying Islands","Uruguay","Uzbekistan","Vanuatu","Venezuela","Viet Nam",
					"Virgin Islands, British","Virgin Islands, U.s.","Wallis And Futuna","Western Sahara","Yemen","Zambia",
					"Zimbabwe" );
		}

	}

}
