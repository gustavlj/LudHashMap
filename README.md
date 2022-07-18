# LudHashMap

An implementation of a Hash Map with linear probing.

Inserting:

- Where an element is placed is determined by the value that was hashed.
- If the position which was hashed to is taken, we linear probe from that position.
- There will always be enough room in the array, since the data structure expands on input.

Removing:

- When we remove an entry, it's marked as "removed"
- An element which is marked with "removed" is no longer consideres as a valid element in the strucutre.
- When we remove elements, we consider shrinking the data structure.
- When we shrink (or expand) the structure, the elements which are marked as "removed" are not copied.

Getting:

- If we use the get-method to try to find an element via a key,
  and the place we hash to is empty, then the element cannot exist.
- If the element is in the structure it would be here, or somewhere later in the structure, which we would
  probe linearly to.
