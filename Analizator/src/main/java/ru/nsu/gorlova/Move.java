package ru.nsu.gorlova;


public record Move(Face face, Turn turn) {
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Move(Face face1, Turn turn1))) return false;
        return this.face == face1 && this.turn == turn1;
    }
    @Override
    public String toString() {
        String str = null;
        switch (turn){
            case CLOCKWISE -> str = face.toString();
            case DOUBLE -> str = face.toString() + "2";
            case COUNTER_CLOCKWISE -> str = face.toString() + "'";
        }
        return str;
    }
}