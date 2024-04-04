package com.example.demo4;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
// import com.ibm.icu.util.Output;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class HelloController {
    @FXML
    private TextField directoryInput;

    @FXML
    private TextArea searchInput;

    @FXML
    private ListView<String> resultList;

    @FXML
    protected void onHelloButtonClick() {
        LuceneTester lucene = new LuceneTester("/tmp",directoryInput.getText(),searchInput.getText());
        ObservableList<String> items = FXCollections.observableList(lucene.getResults());
        resultList.setItems(items);
    }
}