package com.stone.complexrecyclerview.complex;

import java.io.Serializable;

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 14/06/2017 16 58
 */
@SuppressWarnings("all")
public class ComplexPosition implements Serializable {

    private int atomAdapterIndex; //in total Adapters
    private int position; //equivalent RecyclerView's position, include header、footer
    private int sourcePosition; //alike position, but not include header、footer
    private int subPosition; //in IAtomAdapter
    private int subSourcePosition; //alike subPosition, but not include IAtomAdapter'header、IAtomAdapter'footer

    public ComplexPosition(int atomAdapterIndex, int position, int sourcePosition, int subPosition, int subSourcePosition) {
        this.atomAdapterIndex = atomAdapterIndex;
        this.position = position;
        this.sourcePosition = sourcePosition;
        this.subPosition = subPosition;
        this.subSourcePosition = subSourcePosition;
    }

    public int getAtomAdapterIndex() {
        return atomAdapterIndex;
    }

    public void setAtomAdapterIndex(int atomAdapterIndex) {
        this.atomAdapterIndex = atomAdapterIndex;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(int sourcePosition) {
        this.sourcePosition = sourcePosition;
    }

    public int getSubPosition() {
        return subPosition;
    }

    public void setSubPosition(int subPosition) {
        this.subPosition = subPosition;
    }

    public int getSubSourcePosition() {
        return subSourcePosition;
    }

    public void setSubSourcePosition(int subSourcePosition) {
        this.subSourcePosition = subSourcePosition;
    }

    @Override
    public String toString() {
        return "ComplexPosition{" +
                "atomAdapterIndex=" + atomAdapterIndex +
                ", position=" + position +
                ", sourcePosition=" + sourcePosition +
                ", subPosition=" + subPosition +
                ", subSourcePosition=" + subSourcePosition +
                '}';
    }
}
