package edu.msoe.swe2721.lab10;

public class RefactoredItem extends Item {


    /**
     * This method will instantiate a new instance of the item.
     * @param name This is the name of the item.
     * @param sellIn This is the number of days until the item must be sold.
     * @param quality This i9s the initial quality for the item.
     */
    public RefactoredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality() {
        // Handle Sulfuras (legendary item) - no changes needed
        if (name.equals("Sulfuras, Hand of Ragnaros")) {
            return;
        }

        // Decrease sell-in for all non-legendary items
        sellIn--;

        // Handle quality updates based on item type
        if (name.equals("Aged Brie")) {
            increaseQuality();
            if (sellIn < 0) {
                increaseQuality(); // Double increase after sell-by date
            }
        } else if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            increaseQuality();

            // Extra quality increase when 10 days or less
            if (sellIn < 11) {
                increaseQuality();
            }

            // Extra quality increase when 5 days or less
            if (sellIn < 6) {
                increaseQuality();
            }

            // Quality drops to 0 after concert
            if (sellIn < 0) {
                quality = 0;
            }
        } else {
            // Regular items decrease in quality
            decreaseQuality();

            // Double decrease after sell-by date
            if (sellIn < 0) {
                decreaseQuality();
            }
        }
    }

    // Helper method to increase quality with a max cap of 50
    private void increaseQuality() {
        if (quality < 50) {
            quality++;
        }
    }

    // Helper method to decrease quality with a min floor of 0
    private void decreaseQuality() {
        if (quality > 0) {
            quality--;
        }
    }
}