import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

const Suggestions = ({ items, onClick, renderItem }) => (
  <div className="position-relative w-100">
    <div
      className="position-absolute list-group w-100 shadow rounded"
      style={{ maxHeight: "400px", overflowY: "auto" }}
    >
      {items.map(item => (
        <button
          className="list-group-item list-group-item-action"
          key={item.id}
          onMouseDown={() => onClick(item.id)}
        >
          {renderItem(item)}
        </button>
      ))}
    </div>
  </div>
);

export const ChooseInput = ({
  name,
  value,
  onChange,
  onBlur,
  placeholder,
  isInvalid,
  items,
  renderItem,
  filterItem,
}) => {
  const selectedItemId = value;
  const selectedItem = items.find(item => item.id === selectedItemId) || null;
  const [search, setSearch] = useState("");
  const [isFocused, setIsFocused] = useState(false);

  const suggestedItems = items
    .filter(item => filterItem(item, search))
    .slice(0, 10);

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleBlur = () => {
    setTimeout(() => {
      setIsFocused(false);
      if (onBlur) onBlur({ target: { name } });
    }, 100);
  };

  const handleSuggestionClick = itemId => {
    setSearch("");
    setIsFocused(false);
    if (onChange) onChange({ target: { name, value: itemId } });
  };

  const handleClearClick = () => {
    handleSuggestionClick(null);
  };

  if (selectedItemId) {
    return (
      <div
        className="border rounded px-2 d-flex align-items-center"
        style={{ height: "38px" }}
      >
        <div className="flex-grow-1">{renderItem(selectedItem)}</div>
        <button onClick={handleClearClick} className="btn btn-sm btn-link">
          <FontAwesomeIcon icon={faTimes} />
        </button>
      </div>
    );
  } else {
    return (
      <div>
        <input
          className={"form-control " + (isInvalid ? "is-invalid" : "")}
          type="text"
          value={search}
          onChange={event => setSearch(event.target.value)}
          onFocus={handleFocus}
          onBlur={handleBlur}
          placeholder={placeholder}
        />
        {isFocused && (
          <Suggestions
            items={suggestedItems}
            onClick={handleSuggestionClick}
            renderItem={renderItem}
          />
        )}
      </div>
    );
  }
};
