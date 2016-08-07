package wcBookmark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ShopAddController {

    @FXML private Button shop_add_submit;
    @FXML private Button shop_add_cancel;
    @FXML private TextField shop_add_name;
    @FXML private TextField shop_add_location;
    @FXML private ChoiceBox shop_add_country;
    @FXML private TextField shop_add_tel;
    @FXML private TextField shop_add_email;
    @FXML private TextField shop_add_url;
    @FXML private TextArea shop_add_note;

    DbManager db;
    Controller parent;

    public void initialize(){
        ChoiceList choices = new ChoiceList();
        shop_add_country.setItems(choices.getCountryList());
    }

    public void setDatabase(DbManager db){
        this.db = db;
    }
    public void setParent(Controller p){ this.parent = p; }

    @FXML protected void shopAddCancelOnAction(ActionEvent event){
        Stage stage = (Stage) shop_add_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML protected void shopAddSubmitOnAction(ActionEvent event){
        String name = shop_add_name.getText();
        String location = shop_add_location.getText();
        String country = (String) shop_add_country.getValue();
        String tel = shop_add_tel.getText();
        String email = shop_add_email.getText();
        String url = shop_add_url.getText();
        String note = shop_add_note.getText();

        String sql = "INSERT INTO SHOP (NAME, LOCATION, COUNTRY, TEL, EMAIL, WEBSITE, NOTE) VALUES ('"+
                name+"', '"+location+"', '"+country+"', '"+tel+"', '"+email+"', '"+url+"', '"+note+"')";
        db.execute(sql);

        parent.refresh();

        Stage stage = (Stage) shop_add_cancel.getScene().getWindow();
        stage.close();
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
