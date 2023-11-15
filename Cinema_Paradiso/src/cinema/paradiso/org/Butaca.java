/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.paradiso.org;

/**
 *
 * @author abdel
 */
public class Butaca {
    private int row;
    private int column;

    public Butaca(int row, int column) throws Exception {
        setRow(row);
        setColumn(column);
    }
    
    
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) throws Exception {
        if(row <0){
            throw new Exception("Fila no pot ser negativa");
        }
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) throws Exception {
        if(column <0){
            throw new Exception("Columna no pot ser negativa");
        }
        this.column = column;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.row;
        hash = 79 * hash + this.column;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Butaca other = (Butaca) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.column != other.column) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Butaca{" + "row=" + row + ", column=" + column + '}';
    }
    
    
    
}
