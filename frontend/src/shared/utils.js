export const uniqueBy = (array, key) => {
  const seen = new Set();
  return array.filter(item => {
    const k = key(item);
    if (seen.has(k)) {
      return false;
    } else {
      seen.add(k);
      return true;
    }
  });
};

export const sortedBy = (array, key) =>
  [...array].sort((e1, e2) =>
    e1[key] < e2[key] ? -1 : e1[key] > e2[key] ? 1 : 0,
  );
