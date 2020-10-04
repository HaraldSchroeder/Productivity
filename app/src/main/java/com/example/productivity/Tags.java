package com.example.productivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class Tags extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags);

        getSupportActionBar().setTitle("Tags");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!DBSimulator.initialized) {
            DBSimulator.InitializeTestArray();
            DBSimulator.initialized = true;
        }

        DrawTaglist();

        SearchView searchbar = (SearchView) findViewById(R.id.searchbar);
        CharSequence queryHint = searchbar.getQueryHint();
        Log.d("searchbar", queryHint + "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void DrawTaglist() {
        LinearLayout tag_area = findViewById(R.id.tag_area);

        // RELATIVE LAYOUT VERSION
        for (int i = 0; i < DBSimulator.dbTags.length; i++) {
            Log.d("db", "Loop: " + DBSimulator.dbTags[i][1]);
            RelativeLayout tag = new RelativeLayout(this);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            tag.setPadding(5, 5, 5, 5);
            tag.setLayoutParams(params);

            View tagcolor = new View(this);
            RelativeLayout.LayoutParams tagcolorParams = new RelativeLayout.LayoutParams(50, 50);
            tagcolorParams.setMargins(0, 5, 20, 5);
            tagcolor.setLayoutParams(tagcolorParams);
            tagcolor.setBackgroundColor(Integer.parseInt(DBSimulator.dbTags[i][0]));
            tag.addView(tagcolor);

            TextView tagname = new TextView(this);
            tagname.setText(DBSimulator.dbTags[i][1]);
            RelativeLayout.LayoutParams tagstringParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            tagstringParams.setMargins(100, 0 , 0, 0);
            tagname.setLayoutParams(tagstringParams);
            tagname.setId(i);
            tag.addView(tagname);

            Button btnEdit = new Button(this);
            btnEdit.setText("E");
            RelativeLayout.LayoutParams btnEditLP = new RelativeLayout.LayoutParams(100, 100);
            btnEditLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            btnEdit.setLayoutParams(btnEditLP);
            tag.addView(btnEdit);

            btnEdit.setOnClickListener(HandleEditTag(btnEdit, DBSimulator.dbTags[i][1]));

            tag_area.addView(tag);

            View horizontalDivider = new View(this);
            LinearLayout.LayoutParams horizontalDividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            horizontalDivider.setLayoutParams(horizontalDividerParams);
            horizontalDivider.setBackgroundColor(Color.BLACK);

            tag_area.addView(horizontalDivider);
        }
    }

    // Method is called by pushing add tag button
    // Adds a new tag to the collection
    public void onClickAddTag(View view) {
        final EditText tagToBeAdded = new EditText(Tags.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(Tags.this);
        builder.setCancelable(true);
        builder.setTitle("Add new tag");
        builder.setMessage("Der Name fügt sich nicht von allein hinzu, also los jetzt!");
        builder.setView(tagToBeAdded);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddTag(String.valueOf(tagToBeAdded.getText()));
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Edits existing tag
    View.OnClickListener HandleEditTag(final Button btn, final String tagname) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                final EditText tagnameNew = new EditText(Tags.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(Tags.this);
                builder.setCancelable(true);
                builder.setTitle("Bearbeiten: " + tagname);
                builder.setMessage("Namen ändern");
                builder.setView(tagnameNew);
                builder.setPositiveButton("Change name",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RenameTag(tagname, String.valueOf(tagnameNew.getText()));
                            }
                        });
                builder.setNeutralButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DeleteTag(tagname);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

    private void AddTag(String tagname) {
        DBSimulator.AddTag(tagname);
        ClearTagList();
        DrawTaglist();
    }

    private void DeleteTag(String tagname) {
        DBSimulator.DeleteTag(tagname);
        ClearTagList();
        DrawTaglist();
    }

    private void RenameTag(String tagname, String tagnameNew) {
        DBSimulator.RenameTag(tagname, tagnameNew);
        ClearTagList();
        DrawTaglist();
    }

    // Clears view of all tag views
    private void ClearTagList() {
        LinearLayout tag_area = findViewById(R.id.tag_area);
        if (tag_area.getChildCount() > 0) {
            tag_area.removeAllViews();
        }
    }

    // TODO: implement search bar
    private void SearchTag() {

    }

    // TODO: implement unique tags only
    private boolean IsTagUnique(String tagname) {
        return (DBSimulator.FindTag(tagname) == -1);
    }
}

/*
TODOS
- Nur einzigartige Tags -> keine doppelten Namen, gibt sonst Probleme in der Datenbank
        Dazu Methode IsTagUnique weiter ausbauen
- Suchfunktion
 */
