package com.dailyappslab.fino;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailyappslab.fino.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Scanner;

/**
 * Created by GreenQ on 21.02.2015.
 */
public class GameActivity extends Activity {
    public static final String curLevel = "Current Level1";
    private AdView adView;



    OnClickListener CentralImagesOCL;
    OnClickListener RandLettersOCL;
    ImageButton image1;
    ImageButton image2;
    ImageButton image3;
    ImageButton image4;
    TextView textView;
    TextView textViewCurrentLevel;
    TextView textViewGold;
    public int DefaultLevel = 1;
    public int DefaultGold = 200;
    /*SharedPreferences preferences;
    SharedPreferences preferencesOpenedLetters;
    SharedPreferences preferencesGold;
    String Gold = "GOLD";
    String Lvl = "LVL";*/
    StoredPreferences storedPreferences;
    String openedLetters = "OPENED_LETTERS";
    String[] randLettersSet;
    String[] word;
    String[][] storedLetters;
    Letters letters;
    int buttonLetterCounter = 0;
    private Animator mCurrentAnimator;
    private final static int actualLettertId = 200;
    int occupiedContainerCounter = 0;
    boolean openLetterUsed = false;
    boolean removeUnnecessaryLettersUsed = false;
    PopupWindow popupWindow;
    boolean thumbViewHidden = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.game);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.game);

        image1 = (ImageButton) findViewById(R.id.imageView1);
        image2 = (ImageButton) findViewById(R.id.imageView2);
        image3 = (ImageButton) findViewById(R.id.imageView3);
        image4 = (ImageButton) findViewById(R.id.imageView4);

        storedPreferences = new StoredPreferences(this, DefaultLevel, DefaultGold);
        textViewCurrentLevel = (TextView) findViewById(R.id.textViewCurLevel);
        textViewCurrentLevel.setText(String.valueOf(storedPreferences.GetCurrentLevel()));

        textViewGold = (TextView) findViewById(R.id.textViewGold);
        //storedPreferences.EditGoldAmount(99999);
        textViewGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));

        CentralImagesOCL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imageView1:
                        /*EditLevel(GetCurrentLevel() + 1);
                        SetImagesByLevel(GetCurrentLevel());
                        textViewCurrentLevel.setText(String.valueOf(GetCurrentLevel()));
                        CreateGuessWordContainers(GetCurrentLevel());*/
                        //textView.setText(String.valueOf(GetCurrentLevel()));
                        zoomImageFromThumb(findViewById(v.getId()), GetImgId(image2, 1));
                        break;
                    case R.id.imageView2:
                        /*EditLevel(GetCurrentLevel() - 1);
                        SetImagesByLevel(GetCurrentLevel());
                        textViewCurrentLevel.setText(String.valueOf(GetCurrentLevel()));
                        CreateGuessWordContainers(GetCurrentLevel());*/
                        //textView.setText(String.valueOf(GetCurrentLevel()));
                        zoomImageFromThumb(findViewById(v.getId()), GetImgId(image2, 2));
                        break;
                    case R.id.imageView3:
                        zoomImageFromThumb(findViewById(v.getId()), GetImgId(image2, 3));
                        break;
                    case R.id.imageView4:
                        zoomImageFromThumb(findViewById(v.getId()), GetImgId(image2, 4));
                        break;
                }
            }
        };


        image1.setOnClickListener(CentralImagesOCL);
        image2.setOnClickListener(CentralImagesOCL);
        image3.setOnClickListener(CentralImagesOCL);
        image4.setOnClickListener(CentralImagesOCL);

        DrawLevel();
        AdView adView = (AdView)this.findViewById(R.id.adView);
        try
        {
            //adView.setAdSize(AdSize.BANNER);
            //adView.setAdUnitId("ca-app-pub-3376890691318599/3908610460");
            AdRequest adRequest = new AdRequest.Builder().build();
//

            adView.loadAd(adRequest);
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Error occured" + ex.getMessage());
            dlgAlert.setTitle("Error occured");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        //checkCurrentApi();
    }

    private void ShowWinPopUp()
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.win, null);
        final PopupWindow popupWindowWin = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        printGuessedWord(popupWindowWin);
        printQuotation(popupWindowWin);
        Button btnDismiss = (Button)popupView.findViewById(R.id.buttonClose);
        btnDismiss.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                NextLevelClick(null);
                popupWindowWin.dismiss();
            }});

        popupWindowWin.showAtLocation(findViewById(R.id.rootLayout), 0,0,-10);
        }

    private void printQuotation(PopupWindow popupWindow)
    {//return imageView.getResources().getIdentifier("w" + storedPreferences.GetCurrentLevel() + "_" + String.valueOf(btnNum), "drawable", this.getPackageName());

        TextView quotation = (TextView) popupWindow.getContentView().findViewById(R.id.textViewQuotation);
        String quote = getResources().getString(getResources().getIdentifier("q" + storedPreferences.GetCurrentLevel(), "string", this.getPackageName()));
        quotation.setText(quote);
    }
    private void printGuessedWord(PopupWindow popupWindow)
    {
        //setContentView(R.layout.win);
        TextView winWord = (TextView) popupWindow.getContentView().findViewById(R.id.guessWordTextView);
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        for(String s : word) {
            builder.append(s);
            builder.append(" ");
        }
        winWord.setText(builder.toString());
    }
    public void ShowHintsPopUp(View view)
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        final View popupView = layoutInflater.inflate(R.layout.hints, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        Button btnDismiss = (Button)popupView.findViewById(R.id.btnClose);
        btnDismiss.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }});

        RelativeLayout btnOpenLetter = (RelativeLayout)popupView.findViewById(R.id.btnOpenLetter);
        OnClickListener openLetterOcl = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEnoughGold(50)) {
                    openLetter();
                    if (checkWordCorrectness()) {
                        //ShowWinPopUp();
                    }
                    popupWindow.dismiss();
                }
                else
                {
                    popupWindow.dismiss();
                    ShowGameshopPopUp(null);
                }
            }
        };
        btnOpenLetter.setOnClickListener(openLetterOcl);

        RelativeLayout btnOpenWord = (RelativeLayout)popupView.findViewById(R.id.btnOpenWord);
        OnClickListener openWordOCL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEnoughGold(200)) {
                    openWord();
                    if (checkWordCorrectness()) {

                        ShowWinPopUp();
                    }
                    popupWindow.dismiss();
                }
                else
                {
                    popupWindow.dismiss();
                    ShowGameshopPopUp(null);
                }
            }
        };
        btnOpenWord.setOnClickListener(openWordOCL);

        RelativeLayout btnRemoveUnnecessary = (RelativeLayout) popupView.findViewById(R.id.btnRemoveUnnecessary);
        OnClickListener removeUnnecessaryOCL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEnoughGold(200)) {
                    RemoveUnnecessaryLetters(null);
                    popupWindow.dismiss();
                }
                else
                {
                    popupWindow.dismiss();
                    ShowGameshopPopUp(null);
                }
            }
        };
        btnRemoveUnnecessary.setOnClickListener(removeUnnecessaryOCL);
        /*btnOpenWord.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // new android.os.Handler().postDelayed(new Runnable() {
                //  @Override
                // public void run() {
                //
                //openLetter();
                openWord();
                if (checkWordCorrectness()) {

                    ShowWinPopUp();
                }
                //finish();
                // }
                //}, 3000);
                popupWindow.dismiss();
            }});
*/
        /*Button removeUnnecessarryLetters = (Button) findViewById(R.id.btnRemoveUnnecessary);
        removeUnnecessarryLetters.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
               // removeUnnecessaryLetters();
                popupWindow.dismiss();
            }});*/

        popupWindow.showAtLocation(findViewById(R.id.rootLayout), 0,0,-10);
    }
    public void ShowGameshopPopUp(View view)
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        View popupView = layoutInflater.inflate(R.layout.gameshop, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //popupWindow.setBackgroundDrawable(getDrawable(R.drawable.fon2));

        Button btnDismiss = (Button)popupView.findViewById(R.id.btnClose);
        btnDismiss.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                //NextLevelClick(null);
                popupWindow.dismiss();
            }});


        popupWindow.showAtLocation(findViewById(R.id.rootLayout), 0,0,-10);
    }

    private boolean isEnoughGold(int price)
    {
        if(storedPreferences.GetGoldAmount() < price)
            return false;
        else
            return true;
    }
    public void RemoveUnnecessaryLetters(View view)
    {
        int lettersAmount = 0;
        String[][] tempArray = new String[word.length][2];
        for(int i = 0; i < word.length; i++)
        {
            tempArray[i][0] = word[i];
            tempArray[i][1] = null;
        }

        for(int i = 0; i < tempArray.length; i++)
        {

        int index = getLastOccupiedArrayIndex(i, tempArray, tempArray[i][0]);
            if(index == 0)
            {
                for (int j = 0; j < storedLetters.length; j++) {
                    int id = getResources().getIdentifier("buttonLetter" + j, "id", this.getPackageName());
                    Button tempActualButton = (Button) findViewById(id);
                    if (String.valueOf(tempActualButton.getText()).equals(tempArray[i][0])) {
                        if (tempArray[i][1] == null) {
                            //int index = getLastOccupiedArrayIndex()
                            tempArray[i][1] = String.valueOf(j);

                        }
                    }

                }
            }
            else {
                for (int j = index+1; j < storedLetters.length; j++) {
                    int id = getResources().getIdentifier("buttonLetter" + j, "id", this.getPackageName());
                    Button tempActualButton = (Button) findViewById(id);
                    if (String.valueOf(tempActualButton.getText()).equals(tempArray[i][0])) {
                        if (tempArray[i][1] == null) {
                            //int index = getLastOccupiedArrayIndex()
                            tempArray[i][1] = String.valueOf(j);

                        }
                    }
                }
            }
            removeUnnecessaryLettersUsed = true;
        }

        for(int i = 0; i < storedLetters.length; i++)
        {
            int id = getResources().getIdentifier("buttonLetter" + i, "id", this.getPackageName());
            Button tempActualButton = (Button) findViewById(id);

            if(!checkExistance(String.valueOf(i), tempArray))
            {
               tempActualButton.setVisibility(View.INVISIBLE);
            }
        }
        storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()-150);
        DefaultGold = storedPreferences.GetGoldAmount();
        textViewGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));
    }

    private int getLastOccupiedArrayIndex(int currentIndex, String[][] array, String key)
    {
        int index = 0;
        for(int i = 0; i < currentIndex; i++)
        {
            if(String.valueOf(array[i][0]).equals(String.valueOf(key)))
            {
                index = Integer.valueOf(array[i][1]);
            }
        }
        return index;
    }

    private boolean checkExistance(String key, String[][] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            if(String.valueOf(array[i][1]).equals(String.valueOf(key)))
                return true;
        }
        return false;
    }

    private void openWord() {
        for (int i = 0; i < word.length; i++)
        {
            Button tempGuessButton = (Button) findViewById(i);
            tempGuessButton.setText(word[i]);
            /*if(!word[i].equals(tempGuessButton.getText()))
            {
                tempGuessButton.setText(word[i]);


                for(int j = 0; i < storedLetters.length; j++)
                {
                    if(storedLetters[j][0].equals(word[i]))
                    {
                       // if(storedLetters[j][1].equals(null)) {
                            int id = getResources().getIdentifier("buttonLetter" + j, "id", this.getPackageName());
                            Button tempActualButton = (Button) findViewById(id);
                            storedLetters[j][1] = String.valueOf(i);
                            tempActualButton.setVisibility(View.INVISIBLE);
                            tempActualButton.setText("");
                            //tempGuessButton.setBackgroundColor(Color.RED);
                            //return;
                        }
                    //}
                }
               // return;
            }*/
        }
        storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()-200);
        DefaultGold = storedPreferences.GetGoldAmount();
        textViewGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));
    }

    private int getAmountOfCorrectLetters()
    {
        for(int i = 0; i < word.length; i++)
        {
            Button tempGuessButton = (Button) findViewById(i);

            if(!word[i].equals(tempGuessButton.getText())) {
                return i;
            }
        }
        return 0;
    }

    private String[] getParticallyGuessedLetters(int length)
    {
        String[] tempArray = new String[length+1];

        for (int i = 0; i < tempArray.length; i++) {
            Button tempGuessButton = (Button) findViewById(i);
            tempArray[i] = String.valueOf(tempGuessButton.getText());
            //if(word[i] == tempGuessButton.getText()) {
              //  tempArray[i] = String.valueOf(tempGuessButton.getText());

               /* for (int j = 0; j < storedLetters.length; j++) {

                    if ((storedLetters[j][0] == tempArray[i]) && (storedLetters[j][1] == null)) {
                        int id = getResources().getIdentifier("buttonLetter" + j, "id", this.getPackageName());
                        Button tempActualButton = (Button) findViewById(id);
                        tempActualButton.performClick();
                       // storedLetters[j][1] = String.valueOf(i);
                        //tempActualButton.setVisibility(View.INVISIBLE);
                        //tempActualButton.setText("");
                        //tempGuessButton.setBackgroundColor(Color.RED);
                    }*/
               // }
            //}
            //else return tempArray;
        }
        return tempArray;
    }

    private void moveActualLetter(int i)
    {
        for (int j = 0; j < storedLetters.length; j++) {

            if ((storedLetters[j][0].equals(word[i])) && (storedLetters[j][1] == null)) {
                int id = getResources().getIdentifier("buttonLetter" + j, "id", this.getPackageName());
                Button tempActualButton = (Button) findViewById(id);
                tempActualButton.performClick();
                return;
            }
        }
    }
    private void openLetter() {


        int length = getAmountOfCorrectLetters();

        CreateGuessWordContainers(storedPreferences.GetCurrentLevel());
        //String[] temp = getParticallyGuessedLetters(length);
        //if(!removeUnnecessaryLettersUsed) {
            SetLetterImages(randLettersSet);

            occupiedContainerCounter = 0;
       // }

        for(int i = 0; i < length+1; i++) {

           moveActualLetter(i);
        }
       /* for (int i = 0; i < word.length; i++) {
            Button tempGuessButton = (Button) findViewById(i);
        }*/


/*
        for (int i = getAmountOfCorrectLetters(); i < word.length; i++)
        {
            Button tempGuessButton = (Button) findViewById(i);

            if(!(word[i] == tempGuessButton.getText()))
            {
               /* if(!word[i].equals(""))
                {
                    for(int j = 0; j < storedLetters.length; j++) {
                        if(storedLetters[j][0].equals(word[i]) && (storedLetters[j][1].equals(String.valueOf(i))) ) {
                            int id = getResources().getIdentifier("buttonLetter" + j, "id", this.getPackageName());
                            Button tempActualButton = (Button) findViewById(id);
                            tempActualButton.setText(storedLetters[j][0]);
                            tempActualButton.setVisibility(View.VISIBLE);

                        }
                    }
                }*/
                /*tempGuessButton.setText(word[i]);

                for(int j = 0; j < storedLetters.length; j++)
                {
                    if((storedLetters[j][0] == word[i]))
                    {
                        int id = getResources().getIdentifier("buttonLetter" + j, "id", this.getPackageName());
                        Button tempActualButton = (Button) findViewById(id);
                        storedLetters[j][1] = String.valueOf(i);
                        tempActualButton.setVisibility(View.INVISIBLE);
                        tempActualButton.setText("");
                        //tempGuessButton.setBackgroundColor(Color.RED);
                        break;
                    }
                }
                break;
            }

        }*/
        storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()-50);
        DefaultGold = storedPreferences.GetGoldAmount();
        textViewGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));
    }

    private void DrawLevel()
    {
        DefaultGold = storedPreferences.GetGoldAmount();
        DefaultLevel = storedPreferences.GetCurrentLevel();
        SetImagesByLevel(storedPreferences.GetCurrentLevel());
        CreateGuessWordContainers(storedPreferences.GetCurrentLevel());
        GuessWordHandler wordHandler = new GuessWordHandler();
        letters = new Letters();
        randLettersSet = letters.GetRandomStrings(word);
        SetLetterImages(randLettersSet);
    }

    public void NextLevelClick(View view)
    {
        randLettersSet = null;
        word = null;
        storedLetters = null;
        letters = null;
        buttonLetterCounter = 0;
        occupiedContainerCounter = 0;

        storedPreferences.EditLevel(storedPreferences.GetCurrentLevel() + 1);
        SetImagesByLevel(storedPreferences.GetCurrentLevel());
        textViewCurrentLevel.setText(String.valueOf(storedPreferences.GetCurrentLevel()));
        CreateGuessWordContainers(storedPreferences.GetCurrentLevel());
        storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+25);
        textViewGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));
        DrawLevel();
    }

    public void NextLevel()
    {
        ShowWinPopUp();

    }

    public void PrevLevelClick(View view)
    {
        randLettersSet = null;
        word = null;
        storedLetters = null;
        letters = null;
        buttonLetterCounter = 0;
        occupiedContainerCounter = 0;

        storedPreferences.EditLevel(storedPreferences.GetCurrentLevel() - 1);
        SetImagesByLevel(storedPreferences.GetCurrentLevel());
        textViewCurrentLevel.setText(String.valueOf(storedPreferences.GetCurrentLevel()));
        CreateGuessWordContainers(storedPreferences.GetCurrentLevel());

        DrawLevel();
    }

    public void PrevLevel()
    {

    }
    public int getRandLetterIndex(String letter) {
        for (int i = 0; i < randLettersSet.length; i++) {
            if (randLettersSet[i] == letter)
                return i;
        }
        return -1;
    }
    public int getRandContinerIdInStoredLetters(String letter) {
        for (int i = 0; i < storedLetters.length; i++) {
            if (storedLetters[i][0] == letter)
                return Integer.valueOf(storedLetters[i][1]);
        }
        return -1;
    }

    public int getRandContainerId(String particleId/*, int numOfDigits*/) {
        String result;
        char[] chars = particleId.toCharArray();
        Scanner in = new Scanner(particleId).useDelimiter("[^0-9]+");

        //if((Pattern.matches("[0-9]+")) == true )
        //{

        //}
        //switch (numOfDigits) {
          //  case 1:
            //    return Integer.valueOf(String.valueOf(in.nextInt()).toCharArray()[1]);
            //case 2:
                return Integer.valueOf(in.nextInt());
            //default:
              //  return 1;
        //}

    }

    //public int

    private int getEmptyGuessLetterContainer()
    {
        Button guessButton;
        for(int i = 0; i < word.length; i++) {
            guessButton = (Button) findViewById(i);

            if (guessButton.getText() == "")
                return i;
            //storedLetters[containerId][1] = "";
        }
        return -1;
    }

    private int findRandContainerID(String letter)
    {
        for(int i = 0; i < storedLetters.length; i++)
        {
            if(storedLetters[i][0] == letter)
                return i;
        }
        return -1;
    }
    private boolean checkWordCorrectness()
    {
        String guessLetter;
        String guessButtonText;
        Button guessButton;
        String storredArrayLetter;

        /*for(int i = 0; i < word.length; i++){
            guessButton = (Button) findViewById(i);
            guessButtonText = String.valueOf(guessButton.getText());
            storredArrayLetter = storedLetters[findRandContainerID(word[i])][1];
            String wordArrayLetter = word[i];
            //guessLetter = storedLetters[getRandContainerId()][1];
            if(wordArrayLetter != storredArrayLetter)
                return false;
        }*/

        for(int i = 0; i < word.length; i++) {
            guessButton = (Button) findViewById(i);
            guessButtonText = String.valueOf(guessButton.getText());
            guessLetter = word[i];
            if (!guessButtonText.equals(guessLetter)){
                return false;
            }
            //storedLetters[containerId][1] = "";
            //guessButton = null;
        }

        return true;
    }
    public void onClickActualLetter(View v) {

        if (occupiedContainerCounter < word.length) {
            //Button tempButton = (Button) v;
            if(((Button) v).getText() !="") {

                int id = getResources().getIdentifier(String.valueOf(/*actualLettertId +*/ buttonLetterCounter), "id", getPackageName());
                Button guessLetter = (Button) findViewById(getEmptyGuessLetterContainer());
                Button actualLetter = (Button) findViewById((v.getId()));
                guessLetter.setText((actualLetter).getText());
                String name = actualLetter.getResources().getResourceName(actualLetter.getId());
                String containerId = name.substring(name.length() - 2, name.length());
                // randLettersSet[getRandContainerId(containerId)] = String.valueOf(actualLetter.getText());
                int randContainerId = getRandContainerId(containerId/*, 2*/);
                storedLetters[randContainerId][1] = String.valueOf(guessLetter.getId());
                actualLetter.setText("");
                actualLetter.setVisibility(View.INVISIBLE);
                //do {
                //buttonLetterCounter
                //}
                //while(!checkStringEmpty(String.valueOf(guessLetter.getText())));
                //buttonLetterCounter++;
                //использовать двумерный массиив для хранения адреса ячейки рандомной буквы, буквы и адреса ячейки буквы-догадки


                occupiedContainerCounter++;

               // if(occupiedContainerCounter == word.length) {
                    if (checkWordCorrectness()) {
                        /*EditLevel(GetCurrentLevel() + 1);
                        SetImagesByLevel(GetCurrentLevel());
                        textViewCurrentLevel.setText(String.valueOf(GetCurrentLevel()));
                        CreateGuessWordContainers(GetCurrentLevel());*/
                        ShowWinPopUp();
                        //NextLevel();
                    }
                //}
            }
        }
    }

    private boolean checkStringEmpty(String str)
    {
        if(str == "")
            return false;
        else
            return true;
    }

    public void MoveLetterToGuess()
    {
        int id = getResources().getIdentifier(String.valueOf(/*actualLettertId +*/ buttonLetterCounter), "id", this.getPackageName());
        Button temp = (Button) findViewById(id);
        temp.setText(((Button) findViewById(R.id.buttonLetter0)).getText());
        buttonLetterCounter++;
    }
    public void SetLetterImages(String[] wordArray)
    {
        storedLetters = new String[13][3];
        for(int i = 0; i < 13; i++)
        {
            if(i == 13) {
                return;
            }
                //findViewById(R.id.imageButtonLetter1);
                int id = getResources().getIdentifier("buttonLetter" + i, "id", this.getPackageName());
                Button temp = (Button) findViewById(id);
                //temp.
                //temp.setImageResource(getResources().getIdentifier("letter_" + letters.GetLetterIndex(wordArray[i]), "drawable", this.getPackageName()));
                temp.setText(wordArray[i]);
            temp.setVisibility(View.VISIBLE);
            storedLetters[i][0] = wordArray[i];
           // }
        }
    }

    private void SetImagesByLevel(int lvl)
    {
        image1.setImageResource(getResources().getIdentifier("w" + lvl + "_1", "drawable", this.getPackageName()));
        image2.setImageResource(getResources().getIdentifier("w" + lvl + "_2", "drawable", this.getPackageName()));
        image3.setImageResource(getResources().getIdentifier("w" + lvl + "_3", "drawable", this.getPackageName()));
        image4.setImageResource(getResources().getIdentifier("w" + lvl + "_4", "drawable", this.getPackageName()));
        //image4.setImageResource
    }

    private void CreateGuessWordContainers(int lvl)
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.guessWordsLayout);
       // View rootview = View.inflate(getBaseContext(), layout.getId(), false)
        if(((LinearLayout) layout).getChildCount() > 0)
            ((LinearLayout) layout).removeAllViews();
        //wordHandler.BuildWordContainers(layout, this, GetCurrentLevel());

        LinearLayout row = new LinearLayout(getBaseContext());
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //int maxJ = (getResources().getString(R.string.w2)).split("(?!^)").length;
        int id = getResources().getIdentifier("w" + lvl, "string", this.getPackageName());
       // String word = getResources().getString(id);
        word = (getResources().getString(id)).split("(?!^)");

        int maxJ = word.length;
        for (int j = 0; j < maxJ; j++) {
            final Button btnTag = new Button(getBaseContext());
            btnTag.setId(/*actualLettertId +*/ j);
            btnTag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (((Button) v).getText() != "") {
                        int containerId = v.getId();

                        int actualLetterIndex = getActualLettertArrayIndex(containerId);
                        int id = getResources().getIdentifier("buttonLetter" + actualLetterIndex, "id", getPackageName());
                        Button temp = (Button) findViewById(id);
                        temp.setText(storedLetters[Integer.valueOf(actualLetterIndex)][0]);
                        temp.setVisibility(View.VISIBLE);

                        storedLetters[actualLetterIndex][0] = temp.getText().toString();
                        storedLetters[actualLetterIndex][1] = "";
                        btnTag.setText("");
                        occupiedContainerCounter--;
                    }
                }
            });

            btnTag.setTextSize(TypedValue.COMPLEX_UNIT_FRACTION_PARENT, 12);

            btnTag.setPadding(0,0,0,0);

            btnTag.setLayoutParams(new LinearLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setBackgroundColor(Color.BLACK);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(btnTag.getLayoutParams());
            params.width = getResources().getDimensionPixelSize(R.dimen.guess_letter_width);
            params.height = getResources().getDimensionPixelSize(R.dimen.guess_letter_height);
            params.setMargins(2,2,2,2);
            row.setGravity(Gravity.CENTER);
            row.addView(btnTag, params);


        }
        createEaraserButton(row);
        layout.addView(row);
    }

    private void createEaraserButton(LinearLayout row)
    {

        final Button btnTag = new Button(getBaseContext());
        btnTag.setId(R.id.earaseButton);

        btnTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGuessWordContainers(storedPreferences.GetCurrentLevel());
                occupiedContainerCounter = 0;
                SetLetterImages(randLettersSet);
                }

        });

        btnTag.setTextSize(TypedValue.COMPLEX_UNIT_FRACTION_PARENT, 12);
        btnTag.setPadding(0,0,0,0);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnTag.setBackgroundResource(R.drawable.lastik);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(btnTag.getLayoutParams());
        params.width = getResources().getDimensionPixelSize(R.dimen.guess_letter_width);
        params.height = getResources().getDimensionPixelSize(R.dimen.guess_letter_height);
        params.setMargins(getResources().getDimensionPixelSize(R.dimen.eraser_margin_left),2,2,2);
        row.setGravity(Gravity.CENTER);
        row.addView(btnTag, params);
    }

    private int getActualLettertArrayIndex(int guessLetterIndex){
        for(int i = 0; i < 13; i++) {
            if(storedLetters[i][1] == String.valueOf(guessLetterIndex))
                return i;
        }
        return 0;
    }

    private void zoomImageFromThumb(final View thumbView, int imageResId) {

        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.

        if(!thumbViewHidden) {
            thumbView.setAlpha(0f);
            thumbViewHidden = true;
        }
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0);
        expandedImageView.setPivotY(0);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(50);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(50);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                        thumbViewHidden = true;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });

    }

    private int GetImgId(ImageView imageView, int btnNum)
    {
        return imageView.getResources().getIdentifier("w" + storedPreferences.GetCurrentLevel() + "_" + String.valueOf(btnNum), "drawable", this.getPackageName());
    }

    @Override
    public void onBackPressed()
    {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            goBack(null);
        }
    }

    public void goBack(View view)
    {
        Intent output = new Intent();
        output.putExtra("CurrentLevel", DefaultLevel);
        output.putExtra("CurrentGoldAmount", DefaultGold);
        setResult(RESULT_OK, output);
        finish();
    }

    private void checkCurrentApi()
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Current API version: " + Integer.valueOf(Build.VERSION.SDK_INT));
        dlgAlert.setTitle("App Title");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            num1 = data.getIntExtra(Number1Code);
            num2 = data.getIntExtra(Number2Code);
        }
    }*/
}
