package deriktj.lightning_forge.api.recipe;

import com.google.common.collect.ImmutableList;
import deriktj.lightning_forge.common.ModLightningForge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.IItemHandler;
import scala.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeLightningForge {

    private final ItemStack result;
    private final ImmutableList<ItemStack> inputs;
    private final int minimumRodQuality;

    public RecipeLightningForge(ItemStack result, int minimumRodQuality, ItemStack... inputs) {
        this.result = result;
        this.minimumRodQuality = minimumRodQuality;
        ImmutableList.Builder<ItemStack> inputsToSet = ImmutableList.builder();
        int count = 0;
        for(ItemStack stack : inputs) {
            if(count > 4) {
                throw new IllegalArgumentException("Too many ingredients!");
            }
            inputsToSet.add(stack);
            count++;
        }
        for(int i = count; i < 4; i++){
            inputsToSet.add(ItemStack.EMPTY);
        }
        this.inputs = inputsToSet.build();
    }

    public boolean validRecipe(IItemHandler inventory) {
        List<ItemStack> required = new ArrayList<>(inputs);
        boolean[] used = new boolean[4];
        int usedC = 0;
        Arrays.fill(used,false);
        for(int i = 0; i < inventory.getSlots(); i++) {
            if(used[i]) { continue; }
            for(int j = 0; j < required.size(); j++) {
                ItemStack req = required.get(j);
                ItemStack has = inventory.getStackInSlot(i);
                if((req.isEmpty() && has.isEmpty()) || req.isItemEqual(has)) {
                    required.remove(j);
                    usedC++;
                    used[i] = true;
                    break;
                }
            }
        }
        return required.isEmpty() && usedC == 4;
    }

    public boolean validRod(float rodQuality) {
        return rodQuality >= minimumRodQuality;
    }

    public ItemStack getResult() {
        return result.copy();
    }

    public int getMinimumRodQuality() {
        return minimumRodQuality;
    }

    public ImmutableList<ItemStack> getInputs() {
        return inputs;
    }
}

