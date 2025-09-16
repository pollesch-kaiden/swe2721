package edu.msoe.swe2721.lab10;

public class Item {

    /**
     * This is the name for the item that is to be sold.
     */
    protected final String name;

    /**
     * This attribute represents the number of days remaining until which this item must be sold.  It is expressed in days.
     */
    protected int sellIn;

    /**
     * This is a value which represents how valuable the given item is.  The larger this value is, the more valuable it is.  The item is unitless, though it can never be negative.
     */
    protected int quality;

    /**
     * This method will instantiate a new instance of the  item.
     * @param name This is the name of the item.
     * @param sellIn This is the number of days until the item must be sold.
     * @param quality This i9s the initial quality for the item.
     */
    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public int getQuality() {
        return quality;
    }

    /**
     * This method will update the quality of the given item.
     * On each call to this method,  both values will be lowered.
     * Once the sell by date has passed, Quality degrades twice as fast
     * The Quality of an item is never negative
     * "Aged Brie" actually increases in Quality the older it gets
     * The Quality of an item is never more than 50
     * "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
     * "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
     * Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * Quality drops to 0 after the concert
     */
    public void updateQuality() {
        if (!this.name.equals("Aged Brie") && !this.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (this.quality > 0) {
                if (!this.name.equals("Sulfuras, Hand of Ragnaros")) {
                    this.quality = this.quality - 1;
                }
            }
        } else {
            if (this.quality < 50) {
                this.quality = this.quality + 1;

                if (this.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (this.sellIn < 11) {
                        if (this.quality < 50) {
                            this.quality = this.quality + 1;
                        }
                    }

                    if (this.sellIn < 6) {
                        if (this.quality < 50) {
                            this.quality = this.quality + 1;
                        }
                    }
                }
            }
        }

        if (!this.name.equals("Sulfuras, Hand of Ragnaros")) {
            this.sellIn = this.sellIn - 1;
        }

        if (sellIn < 0) {
            if (!this.name.equals("Aged Brie")) {
                if (!this.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (this.quality > 0) {
                        if (!this.name.equals("Sulfuras, Hand of Ragnaros")) {
                            this.quality = this.quality - 1;
                        }
                    }
                } else {
                    this.quality = this.quality - this.quality;
                }
            } else {
                if (this.quality < 50) {
                    this.quality = this.quality + 1;
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
