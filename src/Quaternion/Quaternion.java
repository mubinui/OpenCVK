package Quaternion;

import java.text.DecimalFormat;

public class Quaternion extends Mat33f {

    public Quaternion() {
        this(0, 0, 0, 1);
    }

    /**
     * Define a new quaternion with the given parameters
     *
     * @param x The x component of the quaternion.
     * @param y The y component of the quaternion.
     * @param z The z component of the quaternion.
     * @param w The w component of the quaternion.
     */
    public Quaternion(float x, float y, float z, float w) {
        super(new float[]{ x, y, z, w, 0, 0, 0, 0, 0 });
    }

    public float getX() {
        return m_vec[0];
    }

    public float getY() {
        return m_vec[1];
    }

    public float getZ() {
        return m_vec[2];
    }

    public float getW() {
        return m_vec[3];
    }

    private static final DecimalFormat s_decimalFormatter = new DecimalFormat("#.###");

    @Override
    public String toString() {
        return "Mat33f::Quaternion{ " +
                "x=" + s_decimalFormatter.format(m_vec[0]) + "; " +
                "y=" + s_decimalFormatter.format(m_vec[1]) + "; " +
                "z=" + s_decimalFormatter.format(m_vec[2]) + "; " +
                "w=" + s_decimalFormatter.format(m_vec[3]) + "}";
    }


}
