package com.example.greenq.fino;

/**
 * Created by GreenQ on 22.02.2015.
 */
public class Word {
    public Picture picture;
    //public Picture picture2;
    //public Picture picture3;
    //public Picture picture4;
    public String wordId;

    public Word(String wordId)
    {
        this.wordId = wordId;
        this.picture = getWord();
    }
    public Picture getWord()
    {
        picture = new Picture(wordId);
        return picture;
    }
}
