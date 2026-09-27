# Campus Stock: Full-stack Excel Inventory Manager

Frontend: HTML, CSS, JavaScript. Backend: Python standard-library HTTP server. Data storage: an automatically created Excel workbook (`data/inventory.xlsx`). No Flask, Firebase, MySQL, SQLite, API key or cloud account needed.

## Run on Windows (VS Code)

1. Extract this ZIP. In VS Code choose File → Open Folder and open `College_Inventory_Excel` (the folder containing `server.py`).
2. Open Terminal → New Terminal. Run `python -m pip install -r requirements.txt`.
3. Run `python server.py`.
4. Open `http://127.0.0.1:8000` in a browser. Keep the terminal open while using the app.
5. Add an item. Open `data/inventory.xlsx` in Excel to see the saved row. **Close Excel before using Add/Edit/Delete** because Excel can lock the workbook.
6. Stop the server with Ctrl+C.

If `python` is not recognized on Windows, use `py -m pip install -r requirements.txt` and `py server.py`. If port 8000 is occupied, change `8000` in the final line of `server.py` and use that port in your browser. Do not double-click the HTML file: open the URL above.

## Features

- Add, view, edit, delete and search inventory items
- Stock dashboard: number of items, low-stock items (quantity ≤ 5), sum of quantities
- CSV export, downloadable and openable in Excel
- JSON API: GET/POST `/api/items`, PUT/DELETE `/api/items/{id}`
- Validation: required text fields, nonnegative whole-number quantity and 100-character field limits

## Report outline

Title; abstract; problem statement; objectives; system architecture; frontend design; API and workbook operations; screenshots; example test cases; limitations; future scope. Excel is suitable for a single-user local college demonstration; for multiple simultaneous users or internet deployment, use a proper server database.
