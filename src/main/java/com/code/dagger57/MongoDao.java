package com.code.dagger57;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashSet;

public class MongoDao implements DatabaseDao, AutoCloseable {

    MongoClient mongoClient;
    MongoDatabase archive;
    MongoCollection<Document> keys;
    MongoCollection<Document> lines;

    HashSet set = new HashSet<>();

    public MongoDao(){
        mongoClient = new MongoClient();
        archive = mongoClient.getDatabase("archive");
        keys = archive.getCollection("keys");
        lines = archive.getCollection("lines");
    }

    @Override
    public boolean insert(String key, String line) {
//        int before = set.size();
//        set.add(line);
//        int after = set.size();
//
//        return (after>before);
        Document document = new Document();
        document.append("_id", key);
        try{
            keys.insertOne(document);
        }catch (MongoWriteException e){
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }
}
