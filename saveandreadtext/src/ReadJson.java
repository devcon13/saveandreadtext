import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0


/** API ideas:
 * genius
 */


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;

// Program for print data in JSON format.
    public class ReadJson {
        public String searchTerm = "";
    public JFrame mainFrame;
    public JPanel topPanel;
    public JPanel lowerPanel;
    public JLabel pokeName, statsLabel;
    public JButton lArrow, rArrow;
    public JTextArea stats;
    public JTextArea pokemon;

    public String name;
    public String abilities = "";

    public int cycle = 1;

    public HashMap<Integer, String> whatPoke = new HashMap<>();
    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

        new ReadJson();
        JSONObject file = new JSONObject();
        file.put("Full Name", "Ritu Sharma");
        file.put("Roll No.", new Integer(1704310046));
        file.put("Tution Fees", new Double(65400));


        // To print in JSON format.
        System.out.print(file.get("Tution Fees"));


    }
    public ReadJson() throws ParseException {
        whatPoke.put(1, "ditto");
        whatPoke.put(2, "pikachu");
        whatPoke.put(3, "squirtle");
        whatPoke.put(4, "charizard");
        pull(whatPoke.get(cycle));
        prepareGUI();
    }

        public void pull(String pokemon) throws ParseException {
            String output = "abc";
            String totlaJson="";
            try {

                URL url = new URL("https://pokeapi.co/api/v2/pokemon/"+pokemon);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {

                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));


                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    if(output.contains("[{\"name\":")) {
                        System.out.println(output);
                    }
                    totlaJson+=output;
                }

                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONParser parser = new JSONParser();
            //System.out.println(str);
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totlaJson);
            System.out.println(jsonObject+"hi");

            try {
                System.out.println(jsonObject.get("name"));
                name = (String) jsonObject.get("name");
                //System.out.println(jsonObject.get("abilities"));

                org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("abilities");

                int n =   msg.size(); //(msg).length();
                for (int i = 0; i < n; ++i) {
                    org.json.simple.JSONObject test = (org.json.simple.JSONObject) msg.get(i);
                    org.json.simple.JSONObject test2 = (org.json.simple.JSONObject) test.get("ability");
                    System.out.println(test2.get("name"));
                    abilities=(abilities+((String)test2.get("name"))+"\n");
                   // System.out.println(person.getInt("key"));
                }
                //String name= (String)jsonObject.get("height");
                //System.out.println(name);
            }

            catch (Exception e) {
                e.printStackTrace();
            }




        }
    public void prepareGUI(){
        mainFrame = new JFrame("Pokedeck");
        mainFrame.setLayout(new GridLayout(2,1));

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        pokeName = new JLabel(name, JLabel.CENTER);
        lArrow = new JButton("<");
        lArrow.setActionCommand("back");
        lArrow.addActionListener(new ButtonClickListener());
        rArrow = new JButton(">");
        rArrow.setActionCommand("forward");
        rArrow.addActionListener(new ButtonClickListener());
        pokemon = new JTextArea("image of pokemon goes here");
        topPanel.add(pokeName, BorderLayout.NORTH);
        topPanel.add(lArrow, BorderLayout.WEST);
        topPanel.add(rArrow, BorderLayout.EAST);
        topPanel.add(pokemon, BorderLayout.CENTER);


        lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        statsLabel = new JLabel("Stats", JLabel.LEFT);
        stats = new JTextArea(abilities);
        lowerPanel.add(statsLabel, BorderLayout.NORTH);
        lowerPanel.add(stats, BorderLayout.CENTER);


        mainFrame.add(topPanel);
        mainFrame.add(lowerPanel);
        mainFrame.setSize(800,800);
        mainFrame.setVisible(true);
    }

    public void changeStuff(){
        pokeName.setText(name);
        stats.setText(abilities);
    }


    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            abilities = "";
            try {
                String command = e.getActionCommand();
                if (command.equals("back")) {
                    if(cycle == 1){
                        cycle = 4;
                    } else {
                        cycle--;
                    }
                }
                if (command.equals("forward")) {
                    if(cycle == 4){
                        cycle = 1;
                    } else {
                        cycle++;
                    }
                }
                System.out.println(cycle);
                pull(whatPoke.get(cycle));
                changeStuff();
            } catch (Exception ex) {

            }
        }
    }
    }

