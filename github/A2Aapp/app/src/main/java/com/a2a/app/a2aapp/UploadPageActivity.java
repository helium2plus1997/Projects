package com.a2a.app.a2aapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.a2a.a2a.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mounika on 3/26/2016.
 */
public class UploadPageActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button mupload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mupload = (Button)findViewById(R.id.upload_btn);

        Fragment main = new MainFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, main);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,
                (List<String>) listDataChild);
        expListView.setChoiceMode(expListView.CHOICE_MODE_MULTIPLE);
        expListView.setAdapter(listAdapter);

        mupload.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selected = "";

                int cntChoice = expListView.getCount();
                SparseBooleanArray sparseBooleanArray = expListView.getCheckedItemPositions();
                for (int i = 0; i < cntChoice; i++) {
                    if (sparseBooleanArray.get(i)) {
                        selected += expListView.getItemAtPosition(i).toString() + "\n";
                    }
                }
                Toast.makeText(UploadPageActivity.this, selected,
                        Toast.LENGTH_LONG).show();
            }});
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < expListView.getChildCount(); i++) {
                    if(position == i ){
                        expListView.getChildAt(i).setBackgroundColor(Color.BLUE);
                    }else{
                        expListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });
    }

    /*
     * Preparing the list data
     */

    private void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("FRUITS");
        listDataHeader.add("VEGETABLES");
        listDataHeader.add("BEVERAGES");
        listDataHeader.add("SNACKS");
        listDataHeader.add("STATIONERIES");
        listDataHeader.add("COSMETICS");
        listDataHeader.add("BRANDED FOODS");
        listDataHeader.add("BABY ITEMS");

        // Adding child data
        List<String> fruits = new ArrayList<String>();
        fruits.add("Apples");
        fruits.add("Bananas");
        fruits.add("Oranges");
        fruits.add("Grapes");
        fruits.add("Lemons");
        fruits.add("Strawberries");

        // Adding child data
        List<String> vegetables = new ArrayList<String>();
        vegetables.add("Spinach");
        vegetables.add("Mushrooms");
        vegetables.add("Carrots");
        vegetables.add("Tomatoes");
        vegetables.add("Onions");
        vegetables.add("Peppers");
        vegetables.add("Corn");
        // Adding child data
        List<String> beverages = new ArrayList<String>();
        beverages.add("Water");
        beverages.add("Juice");
        beverages.add("Soda");
        beverages.add("Vodka");
        beverages.add("Beer");
        beverages.add("Wine");
        // Adding child data
        List<String> snacks = new ArrayList<String>();
        snacks.add("Cookies");
        snacks.add("Chips");
        snacks.add("Crackers");
        snacks.add("Raisins");
        snacks.add("Chocolates");
        snacks.add("Nuts");
        snacks.add("Popcorn");
        snacks.add("Candies");
        // Adding child data
        List<String> cosmetics = new ArrayList<String>();
        cosmetics.add("Airwick");
        cosmetics.add("AquaFresh");
        cosmetics.add("Braun");
        cosmetics.add("Dove");
        cosmetics.add("Olay");
        cosmetics.add("Ponds");
        cosmetics.add("Parachute");
        cosmetics.add("BodyLotion");
        cosmetics.add("Ivory");
        cosmetics.add("Secret");
        cosmetics.add("Spray N Wash");
        cosmetics.add("Puffs");

        // Adding child data
        List<String> Brandedfoods = new ArrayList<String>();
        Brandedfoods.add("Chips");
        Brandedfoods.add("Crisps");
        Brandedfoods.add("Corn Snacks");
        Brandedfoods.add("Nuts");
        Brandedfoods.add("Seeds");
        Brandedfoods.add("Condiments");
        Brandedfoods.add("Barbecue sauces");
        Brandedfoods.add("Hot sauces");
        Brandedfoods.add("Mayonnaise");
        Brandedfoods.add("Mustards");
        Brandedfoods.add("Salad dressings");
        Brandedfoods.add("Breakfast foods");
        Brandedfoods.add("Crackers and other savoury biscuits");
        Brandedfoods.add("Cookies");
        Brandedfoods.add("Cakes");

        // Adding child data
        List<String> babyitems = new ArrayList<String>();

        babyitems.add("Wipes");
        babyitems.add("Diapers");
        babyitems.add("Formula");
        babyitems.add("Jar Food");
        babyitems.add("Powder");
        babyitems.add("Shampoo");
        babyitems.add("Medication");
        babyitems.add("Water");
        babyitems.add("Juices");
        babyitems.add("Lotion");
        babyitems.add("Rash Cream");

        listDataChild.put(listDataHeader.get(0), fruits); // Header, Child data
        listDataChild.put(listDataHeader.get(1), vegetables);
        listDataChild.put(listDataHeader.get(2), beverages);
        listDataChild.put(listDataHeader.get(3), snacks);
        listDataChild.put(listDataHeader.get(4), cosmetics);
        listDataChild.put(listDataHeader.get(5), Brandedfoods);
        listDataChild.put(listDataHeader.get(6), babyitems);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"settings Clicked",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(),"You have been Logout",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_done:
                Bundle args = new Bundle();
                args.putString("Menu", "You pressed done button.");
                Fragment detail = new TextFragment();
                detail.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, detail).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            String voice_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            Toast.makeText(getApplicationContext(),voice_text,Toast.LENGTH_LONG).show();
        }
    }
}

