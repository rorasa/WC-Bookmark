

# Compuler parameters
PACKAGE = wcBookmark
JFLAGS = -g
JC = javac
OUTPATH = ./bin
CPATH = ./lib/derby.jar
SRCPATH = ./src/$(PACKAGE)

# External resources
RESOURCES = \
	WC-Bookmark.fxml \
	shop_add.fxml \
	placeholder.png

default: classes resources

classes:
	$(JC) -d $(OUTPATH) -cp $(CPATH) $(JFLAGS) $(SRCPATH)/*.java

resources: WC-Bookmark.fxml shop_add.fxml placeholder.png

WC-Bookmark.fxml:
	cp $(SRCPATH)/WC-Bookmark.fxml $(OUTPATH)/$(PACKAGE)/WC-Bookmark.fxml

shop_add.fxml:
	cp $(SRCPATH)/shop_add.fxml $(OUTPATH)/$(PACKAGE)/shop_add.fxml

placeholder.png:
	cp $(SRCPATH)/placeholder.png $(OUTPATH)/$(PACKAGE)/placeholder.png

clean:
	rm -r $(OUTPATH)/$(PACKAGE)/*.class

purge:
	rm -r $(OUTPATH)/$(PACKAGE)

run:
	java -cp .:$(CPATH):$(OUTPATH) $(PACKAGE).Main
