function preorder(u) {
    if u is null then return
    print u.label
    r = either 0 or 1 with 50% probability
    if r == 0
        preorder(u.left_child)
        preorder(u.right_child)
    if r == 1
        preorder(u.right_child)
        preorder(u.left_child)
}