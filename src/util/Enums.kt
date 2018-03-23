package util

enum class CollisionType {
    NONE,
    NORMAL,
    GRASS,
    DARK_GRASS,
    LEFT_CLIFF,
    RIGHT_CLIFF,
    DOWN_CLIFF,
    SIGN,
    PLAYER,
    NPC
}

enum class InputKey {
    A,
    B,
    SEL,
    START,
    L,
    R
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}