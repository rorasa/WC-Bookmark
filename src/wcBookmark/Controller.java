package wcBookmark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Controller {

    @FXML private ChoiceBox bookmark_type;
    @FXML private TextField bookmark_name;
    @FXML private ChoiceBox shop_country;

	@FXML private TextArea expert_in;
	@FXML private TextArea expert_out;
	@FXML private Button expert_submit;

	@FXML private Button shop_add;

	DbManager db;

    public void initialize(){

        ChoiceList choices = new ChoiceList();
        bookmark_type.setItems(choices.getMaterialList());
        bookmark_type.setValue("Leather");

        shop_country.setItems(choices.getCountryList());
    }

	public void setDatabase(DbManager db){
		this.db = db;
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

	@FXML protected void shopAddOnAction(ActionEvent event) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("shop_add.fxml"));
		Parent root = loader.load();
		ShopAddController controller = loader.getController();
		controller.setDatabase(db);

		Stage stage = new Stage();
		stage.setTitle("Add new shop");
		stage.setScene(new Scene(root, 1000, 800));
		stage.show();
	}

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
