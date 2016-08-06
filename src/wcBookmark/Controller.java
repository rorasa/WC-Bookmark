package wcBookmark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

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
				ResultSet rs = db.statement.getResultSet();
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
				outText += "\nOperation finished successfully: "+db.statement.getUpdateCount()+" updated.";
			}else{
				outText += "Operation finished successfully: "+db.statement.getUpdateCount()+" updated.";
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		expert_out.setText(outText);

		expert_in.setText("");
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
		            "Chad","Chile","China","Christmas Island","Cocos (keeling) Islands","Colombia","Comoros",
		            "Congo","Congo, The Democratic Republic Of The","Cook Islands","Costa Rica","Cote D'ivoire",
		            "Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic",
		            "East Timor","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia",
		            "Falkland Islands (malvinas)","Faroe Islands","Fiji","Finland","France","French Guiana",
		            "French Polynesia","French Southern Territories","Gabon","Gambia","Georgia","Germany",
		            "Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea",
		            "Guinea-bissau","Guyana","Haiti","Heard Island And Mcdonald Islands","Holy See (vatican City State)","Honduras",
		            "Hong Kong","Hungary","Iceland","India","Indonesia","Iran, Islamic Republic Of",
		            "Iraq","Ireland","Israel","Italy","Jamaica","Japan",
		            "Jordan","Kazakstan","Kenya","Kiribati","Korea, Democratic People's Republic Of","Korea, Republic Of",
		            "Kosovo","Kuwait","Kyrgyzstan","Lao People's Democratic Republic","Latvia","Lebanon",
		            "Lesotho","Liberia","Libyan Arab Jamahiriya","Liechtenstein","Lithuania","Luxembourg",
		            "Macau","Macedonia, The Former Yugoslav Republic Of","Madagascar","Malawi","Malaysia","Maldives",
		            "Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius",
		            "Mayotte","Mexico","Micronesia, Federated States Of","Moldova, Republic Of","Monaco","Mongolia",
		            "Montserrat","Montenegro","Morocco","Mozambique","Myanmar","Namibia",
		            "Nauru","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand",
		            "Nicaragua","Niger","Nigeria","Niue","Norfolk Island","Northern Mariana Islands",
		            "Norway","Oman","Pakistan","Palau","Palestinian Territory, Occupied","Panama",
		            "Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn","Poland",
		            "Portugal","Puerto Rico","Qatar","Reunion","Romania","Russian Federation",
		            "Rwanda","Saint Helena","Saint Kitts And Nevis","Saint Lucia","Saint Pierre And Miquelon",
		            "Saint Vincent And The Grenadines",
		            "Samoa","San Marino","Sao Tome And Principe","Saudi Arabia","Senegal","Serbia",
		            "Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands",
		            "Somalia","South Africa","South Georgia And The South Sandwich Islands","Spain","Sri Lanka","Sudan",
		            "Suriname","Svalbard And Jan Mayen","Swaziland","Sweden","Switzerland","Syrian Arab Republic",
		            "Taiwan, Province Of China","Tajikistan","Tanzania, United Republic Of","Thailand","Togo","Tokelau",
		            "Tonga","Trinidad And Tobago","Tunisia","Turkey","Turkmenistan","Turks And Caicos Islands",
		            "Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States",
		            "United States Minor Outlying Islands","Uruguay","Uzbekistan","Vanuatu","Venezuela","Viet Nam",
		            "Virgin Islands, British","Virgin Islands, U.s.","Wallis And Futuna","Western Sahara","Yemen","Zambia",
		            "Zimbabwe" );
        }

    }
}
