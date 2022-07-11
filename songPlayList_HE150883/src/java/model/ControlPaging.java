/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class ControlPaging {
    private int nrpp; //number records per page
    private int cp; //current page
    private int np;
    private int size;
    private int begin;
    private int end;
    
    public ControlPaging(int nrpp, int cp, int size) {
        this.nrpp = nrpp;
        this.cp = cp;
        this.size = size;
    }

    public ControlPaging() {
    }

    public int getNrpp() {
        return nrpp;
    }

    public void setNrpp(int nrpp) {
        this.nrpp = nrpp;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getNp() {
        return np;
    }

    public void setNp(int np) {
        this.np = np;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    
//      int nrpp = 7;
//        int cp = 0;
     //322 
    
    public void calc(){
        np = (size + nrpp - 1)/ nrpp;
        
        cp = cp < 0 ? 0: cp;
        cp = cp > np - 1? np-1:cp;
        
        begin = cp * nrpp;
        end = (cp + 1) * nrpp - 1;
        end = (end >= size) ? (size - 1): end;
        
    
        
        pageStart = cp - 1;
        pageStart = pageStart < 0? 0: pageStart;
        
        pageEnd = cp + 1;
        pageEnd = pageEnd > np - 1? np-1:pageEnd;
        
        pagePre = cp - 3;
        pagePre = pagePre < 0 ? 0: pagePre;
        
        
        pageNext = cp + 3;
        pageNext = pageNext > np - 1? np-1:pageEnd;
    }
    
    private int pageStart;
    private int pageEnd;
    private int pagePre;
    private int pageNext;

    public int getPagePre() {
        return pagePre;
    }

    public void setPagePre(int pagePre) {
        this.pagePre = pagePre;
    }

    public int getPageNext() {
        return pageNext;
    }

    public void setPageNext(int pageNext) {
        this.pageNext = pageNext;
    }
    

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }
    
    
}
