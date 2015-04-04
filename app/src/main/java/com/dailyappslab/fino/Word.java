package com.dailyappslab.fino;

/**
 * Created by GreenQ on 22.02.2015.
 */
public class Word {
    public Picture picture;
    //public Picture picture2;
    //public Picture picture3;
    //public Picture picture4;
    public String wordId;
    public String[] werbs =  {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М",
            "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я"};



    public Word(String wordId)
    {
        this.wordId = wordId;
        this.picture = getWord();

        //werbs
    }
    public Picture getWord()
    {
       // picture = new Picture(wordId);
        return null;
    }
}
