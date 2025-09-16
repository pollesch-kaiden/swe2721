package edu.msoe.swe2721.lab10;

/**
 * This class implements the Gilded Rose Problem.
 * @see <a href="https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md">https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md</a>
 */
class GildedRose {
    /**
     * This is the array of items that are to be managed.
     */
    Item[] items;

    /**
     * This will create a new list of items.
     * @param items This si the list of items that is to be managed.
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * This method will update the quality of each object.
     */
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            items[i].updateQuality();
        }
    }
}
