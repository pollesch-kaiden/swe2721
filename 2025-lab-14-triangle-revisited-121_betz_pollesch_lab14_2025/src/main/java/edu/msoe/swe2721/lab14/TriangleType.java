package edu.msoe.swe2721.lab14;

/**
 * This enumeration defines the triangle types.
 */
public enum TriangleType {
        /**
         * A triangle which is invlaid, i.e. the sides do not sum up appropriately, etc.
         */
        INVALID,
        /**
         * A triangle in which no two sides have the same length.
         */
        SCALENE,

        /**
         * A triangle in which exactly 2 sides have the same length.
         */
        ISOSCELES,

        /**
         * A triangle in which all 3 sides have the same length.
         */
        EQUILATERAL;
}
