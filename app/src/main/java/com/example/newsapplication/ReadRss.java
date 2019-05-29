package com.example.newsapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadRss extends AsyncTask<Void, Void, Void> {
    Context context;
    String address = "https://www.sciencemag.org/rss/news_current.xml";
    ProgressDialog progressDialog;
    URL url;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;

    public ReadRss(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter myAdapter = new MyAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProcessXml(GetData());
        return null;
    }

    private void ProcessXml(Document document) {
        if (document != null) {
            feedItems = new ArrayList<>();
            Element root = document.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i=0; i<items.getLength(); i++){
                Node currentChild = items.item(i);
                if(currentChild.getNodeName().equalsIgnoreCase("item")){
                    NodeList itemChilds = currentChild.getChildNodes();
                    FeedItem item = new FeedItem();
                    for (int j=0; j < itemChilds.getLength(); j++){
                        Node current = itemChilds.item(j);
                        if(current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                        } else if(current.getNodeName().equalsIgnoreCase("description")){
                            item.setDesc(current.getTextContent());
                        } else if(current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(current.getTextContent());
                        } else if(current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        } else if(current.getNodeName().equalsIgnoreCase("media:thumbnail")){
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setThumbnail(url);
                        }
                    }
                    feedItems.add(item);
                    Log.d("itemThumbnail", item.getThumbnail());
                }
            }
        }
    }

    public Document GetData() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document xmlDoc = documentBuilder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
