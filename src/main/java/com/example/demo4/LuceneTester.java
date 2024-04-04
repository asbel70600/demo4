package com.example.demo4;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.util.Output;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
   private String indexDir = "/tmp";
   private String dataDir = "/home/asbel/Documents";
   private String query;
   private Indexer indexer;
   private Searcher searcher;

   private ArrayList<String> results;

   public LuceneTester(String indexDir,String dataDir,String queryRaw){
      this.indexDir = indexDir;
      this.dataDir = dataDir;

      String query_raw = queryRaw;
      String query_string = "";

      try {
         String line;
         final StringBuilder output = new StringBuilder();
         final String[] command = {"/home/asbel/projects/si/tagInput.sh",query_raw};

         final Process proc = Runtime.getRuntime().exec(command);
         final BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

         while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
         }

         final int exitVal = proc.waitFor();

         if (exitVal == 0) {
            query_string = output.toString();
            this.query = query_string;
         } else {
            System.out.println("ERRORRRRRRRR pinnnnnngaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.exit(1);
         }

      } catch (IOException e) {
         e.printStackTrace();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }


      System.out.println(query_string);
      try {
         createIndex();
         search(query_string);
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   public String getQuery() {
      return query;
   }
   public ArrayList<String> getResults(){
      return results;
   }

   private void createIndex() throws IOException {
      indexer = new Indexer(indexDir);
      int numIndexed;
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
      indexer.close();
   }

   private void search(String searchQuery) throws IOException, ParseException {
      searcher = new Searcher(indexDir);
      TopDocs hits = searcher.search(searchQuery);
      this.results = new ArrayList<String>();

      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
         this.results.add(doc.get(LuceneConstants.FILE_PATH));
//           System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));
      }
   }
}
