package TreeSecond;

/**
 * Класс, представляющий красно-черное дерево.
 */
public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    public static int COUNT_OF_OPERATIONS = 0;

    private Node root;

    /**
     * Проверяет, является ли узел красным.
     *
     * @param node Узел для проверки
     * @return true, если узел красный, иначе false
     */
    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    /**
     * Вставляет ключ в красно-черное дерево.
     *
     * @param key Ключ для вставки
     */
    public void insert(int key) {
        root = insert(root, key);
        root.color = BLACK;
    }

    /**
     * Рекурсивно вставляет ключ в поддерево с заданным корнем.
     *
     * @param node Корень поддерева
     * @param key  Ключ для вставки
     * @return Корень поддерева после вставки
     */
    private Node insert(Node node, int key) {
        COUNT_OF_OPERATIONS++;
        if (node == null) return new Node(key, RED);

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        return node;
    }

    /**
     * Выполняет левый поворот вокруг заданного узла.
     *
     * @param node Узел для поворота
     * @return Новый корень поддерева после поворота
     */
    private Node rotateLeft(Node node) {
        COUNT_OF_OPERATIONS++;
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    /**
     * Выполняет правый поворот вокруг заданного узла.
     *
     * @param node Узел для поворота
     * @return Новый корень поддерева после поворота
     */
    private Node rotateRight(Node node) {
        COUNT_OF_OPERATIONS++;
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    /**
     * Меняет цвета узлов.
     *
     * @param node Узел, чьи цвета меняются
     */
    private void flipColors(Node node) {
        COUNT_OF_OPERATIONS++;
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    /**
     * Проверяет, содержит ли дерево заданный ключ.
     *
     * @param key Ключ для проверки
     * @return true, если ключ содержится в дереве, иначе false
     */
    public boolean contains(int key) {
        return contains(root, key);
    }

    /**
     * Рекурсивно проверяет, содержит ли поддерево с заданным корнем заданный ключ.
     *
     * @param node Корень поддерева
     * @param key  Ключ для проверки
     * @return true, если ключ содержится в поддереве, иначе false
     */
    private boolean contains(Node node, int key) {
        COUNT_OF_OPERATIONS++;
        if (node == null) return false;

        if (key < node.key) {
            return contains(node.left, key);
        } else if (key > node.key) {
            return contains(node.right, key);
        } else {
            return true;
        }
    }

    /**
     * Удаляет ключ из дерева, если он существует.
     *
     * @param key Ключ для удаления
     */
    public void delete(int key) {
        if (!contains(key)) return;

        // Если корень нечёрный, делаем его чёрным
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (root != null) root.color = BLACK;
    }

    /**
     * Рекурсивно удаляет ключ из поддерева с заданным корнем.
     *
     * @param node Корень поддерева
     * @param key  Ключ для удаления
     * @return Корень поддерева после удаления
     */
    private Node delete(Node node, int key) {
        COUNT_OF_OPERATIONS++;
        if (key < node.key) {
            if (!isRed(node.left) && !isRed(node.left.left))
                node = moveRedLeft(node);
            node.left = delete(node.left, key);
        } else {
            if (isRed(node.left))
                node = rotateRight(node);
            if (key == node.key && (node.right == null))
                return null;
            if (!isRed(node.right) && !isRed(node.right.left))
                node = moveRedRight(node);
            if (key == node.key) {
                Node minRight = min(node.right);
                node.key = minRight.key;
                node.right = deleteMin(node.right);
            } else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
    }

    /**
     * Находит минимальный узел в поддереве с заданным корнем.
     *
     * @param node Корень поддерева
     * @return Минимальный узел
     */
    private Node min(Node node) {
        COUNT_OF_OPERATIONS++;
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    /**
     * Удаляет минимальный узел из поддерева с заданным корнем.
     *
     * @param node Корень поддерева
     * @return Корень поддерева после удаления минимального узла
     */
    private Node deleteMin(Node node) {
        COUNT_OF_OPERATIONS++;
        if (node.left == null)
            return null;

        if (!isRed(node.left) && !isRed(node.left.left))
            node = moveRedLeft(node);

        node.left = deleteMin(node.left);
        return balance(node);
    }

    /**
     * Выполняет операцию перемещения красной вершины влево.
     *
     * @param node Узел для операции перемещения
     * @return Новый корень поддерева после операции перемещения
     */
    private Node moveRedLeft(Node node) {
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    /**
     * Выполняет операцию перемещения красной вершины вправо.
     *
     * @param node Узел для операции перемещения
     * @return Новый корень поддерева после операции перемещения
     */
    private Node moveRedRight(Node node) {
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    /**
     * Балансирует дерево после операций вставки и удаления.
     *
     * @param node Корень поддерева
     * @return Новый корень поддерева после балансировки
     */
    private Node balance(Node node) {
        if (isRed(node.right)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);
        return node;
    }
}
