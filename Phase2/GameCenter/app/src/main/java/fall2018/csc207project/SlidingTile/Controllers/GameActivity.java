package fall2018.csc207project.SlidingTile.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;

import fall2018.csc207project.SlidingTile.Models.BoardManager;
import fall2018.csc207project.SlidingTile.Views.BoardGameView;
import fall2018.csc207project.SlidingTile.Views.GestureDetectGridView;
import fall2018.csc207project.R;
import fall2018.csc207project.SlidingTile.Views.NumberPickerDialog;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements BoardGameView{

    private static int columnWidth, columnHeight;

    private EditText undoText;

    /**
     * The buttons to display.
     */
    private List<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private BoardGamePresenter presenter;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    @Override
    public void display() {
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new BoardGamePresenter(this, getApplicationContext());
        BoardManager boardManager = (BoardManager) getIntent().getSerializableExtra("save");
        this.tileButtons = boardManager.getButtonList(this);
        setContentView(R.layout.activity_main);
        undoText = findViewById(R.id.StepsToUndo);
        setupGridView(boardManager);
        addUndoButtonListener();
        addStepInputListener();
    }

    /**
     * Setup the customized grid view and get the height and weight of the tiles.
     */
    private void setupGridView(final BoardManager boardManager){
        presenter.setBoardManager(boardManager);
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setBoardGamePresenter(presenter);
        gridView.setNumColumns(boardManager.getComplexity());
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getComplexity();
                        columnHeight = displayHeight / boardManager.getComplexity();
                        display();
                    }
                });
    }

    /**
     * Active the listener for the step picker edit text.
     */
    private void addStepInputListener(){
        undoText.setText("0");
        undoText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.onUndoTextClicked();
            }
        });
    }
    /**
     * Activate the start button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.onUndoButtonClicked(Integer.parseInt(undoText.getText().toString()));
            }
        });
    }

    /**
     * Display that a game has No Undo Times Left.
     */
    public void makeToastNoUndoTimesLeftText() {
        Toast.makeText(this, "No times or undo out of limit!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Swap the buttons of position pos1 and pos2.
     */
    public void swapButtons(int pos1, int pos2){
        Collections.swap(tileButtons, pos1, pos2);
        display();
    }

    /**
     *Show the number picker dialog.
     */
    public void showNumberPicker(){
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                undoText.setText(Integer.toString(numberPicker.getValue()));
            }
        });
        newFragment.show(getSupportFragmentManager(), "time picker");
    }
}
