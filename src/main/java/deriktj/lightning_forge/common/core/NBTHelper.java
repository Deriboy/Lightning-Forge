package deriktj.lightning_forge.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

    public static NBTTagCompound getNBT(ItemStack stack) {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }



    public static boolean verifyExistence(ItemStack stack, String key) {
        return !stack.isEmpty() && getNBT(stack).hasKey(key);
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        getNBT(stack).setBoolean(key, value);
    }

    public static boolean getBoolean(ItemStack stack, String key, boolean defaultExpected) {
        return verifyExistence(stack, key) ? getNBT(stack).getBoolean(key) : defaultExpected;
    }

    public static void setInteger(ItemStack stack, String key, int value) {
        getNBT(stack).setInteger(key, value);
    }

    public static int getInteger(ItemStack stack, String key, int defaultExpected) {
        return verifyExistence(stack, key) ? getNBT(stack).getInteger(key) : defaultExpected;
    }
}
