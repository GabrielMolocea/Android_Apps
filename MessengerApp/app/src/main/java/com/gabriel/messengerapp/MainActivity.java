package com.gabriel.messengerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements RoomListener {

    private String channelID = "Add code frome Scaledrone";
    private String romeName = "observable_room";
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        MemberData data = new MemberData(getRandomName(), getRandomColor());

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");

                scaledrone.subscribe(romeName, MainActivity.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
    }


    private String getRandomName() {
        String[] adj = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry",
                "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring",
                "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered",
                "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green",
                "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red",
                "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered",
                "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud",
                "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};

        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning",
                "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest",
                "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush",
                "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze",
                "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky",
                "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water",
                "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice",
                "paper", "frog", "smoke", "star"};

        return (adj[(int) Math.floor(Math.random() * adj.length)] + "-" +
                    nouns[(int) Math.floor(Math.random() * nouns.length)]);
    }



    private String getRandomColor() {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer("#");
        while (stringBuffer.length() < 7) {
            stringBuffer.append(Integer.toHexString(random.nextInt()));
        }
        return stringBuffer.toString().substring(0,7);
    }


    public void sendMessage(View view) {
        String message =  editText.getText().toString();
        if (message.trim().length() > 0) {
            scaledrone.publish(romeName, message);
            editText.getText().clear();
        }
    }
    @Override
    public void onOpen(Room room) {
        System.out.println("Connected to room");
        scaledrone.subscribe(romeName, MainActivity.this);
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }


    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
            final ObjectMapper mapper = new ObjectMapper();

            try {
                final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(),
                        MemberData.class);
                boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
                final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageAdapter.add(message);
                        messagesView.setSelection(messagesView.getCount() - 1);
                    }
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
    }
}

class MemberData{
    private String name;
    private String color;

    public MemberData(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public MemberData() {

    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}