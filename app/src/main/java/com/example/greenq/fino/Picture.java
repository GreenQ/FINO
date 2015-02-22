package com.example.greenq.fino;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by GreenQ on 22.02.2015.
 */
public class Picture {
    //String path[] = new String[4];
    List<String> sources = new List<String>() {
        @Override
        public void add(int location, String object) {

        }

        @Override
        public boolean add(String object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public String get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int location) {
            return null;
        }

        @Override
        public String remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public String set(int location, String object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<String> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };

    public Picture(String wordId)
    {
        getPictureById(wordId);
        //this.path = "@+res/assets/" + wordId;
    }

    private void getPictureById(String wordId)
    {
        //sources = new List<String>();
        sources.add("@+res/assets/w" + wordId + "_1.jpg");
        sources.add("@+res/assets/w" + wordId + "_2.jpg");
        sources.add("@+res/assets/w" + wordId + "_3.jpg");
        sources.add("@+res/assets/w" + wordId + "_4.jpg");
    }
}
