package Quaternion;

public class Mat33f {
    protected float m_vec[];

    public Mat33f(final float vec[]) {
        if (vec == null)
            throw new NullPointerException("vec may not be null");
        if (vec.length != 9)
            throw new IllegalArgumentException("vec must be length 9");
        m_vec = vec.clone();
    }

    public float[] toArray() {
        return m_vec.clone();
    }

}
