package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;

public class IntRangeList extends AbstractDoubleList
{
    private final int field_197864_a;
    private final int startOffset;

    IntRangeList(int p_i47684_1_, int p_i47684_2_)
    {
        this.field_197864_a = p_i47684_1_;
        this.startOffset = p_i47684_2_;
    }

    public double getDouble(int p_getDouble_1_)
    {
        return (double)(this.startOffset + p_getDouble_1_);
    }

    public int size()
    {
        return this.field_197864_a + 1;
    }
}
