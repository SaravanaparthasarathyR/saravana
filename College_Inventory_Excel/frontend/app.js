const $ = id => document.getElementById(id);
let rows = [], editing = null;
async function api(path, options) {
  const response = await fetch(path, options);
  const body = await response.json();
  if (!response.ok) throw new Error(body.error || 'Request failed');
  return body;
}
function cell(text) { const td = document.createElement('td'); td.textContent = text; return td; }
function render() {
  $('count').textContent = rows.length;
  $('low').textContent = rows.filter(x => x.quantity <= 5).length;
  $('total').textContent = rows.reduce((n, x) => n + x.quantity, 0);
  const term = $('search').value.trim().toLowerCase();
  const filtered = rows.filter(x => [x.name, x.category, x.location].some(y => y.toLowerCase().includes(term)));
  $('items').replaceChildren();
  for (const item of filtered) {
    const tr = document.createElement('tr');
    const quantity = cell(item.quantity);
    if (item.quantity <= 5) quantity.className = 'qty-low';
    tr.append(cell(item.name), cell(item.category), quantity, cell(item.location));
    const buttons = document.createElement('td');
    for (const [label, action] of [['Edit', () => edit(item)], ['Delete', () => remove(item)]]) {
      const button = document.createElement('button');
      button.textContent = label; button.onclick = action; buttons.append(button);
    }
    tr.append(buttons); $('items').append(tr);
  }
  $('empty').hidden = filtered.length !== 0;
}
async function refresh() { rows = await api('/api/items'); render(); }
function clear() { editing = null; $('form').reset(); $('form-title').textContent = 'Add an item'; $('save').textContent = 'Save item'; $('cancel').hidden = true; }
function edit(item) {
  editing = item.id;
  for (const key of ['name', 'category', 'quantity', 'location']) $(key).value = item[key];
  $('form-title').textContent = 'Edit item'; $('save').textContent = 'Update item'; $('cancel').hidden = false;
  window.scrollTo({top: 0, behavior: 'smooth'});
}
async function remove(item) {
  if (!confirm(`Delete ${item.name}?`)) return;
  try { await api(`/api/items/${item.id}`, {method: 'DELETE'}); await refresh(); }
  catch (e) { $('message').textContent = e.message; }
}
$('form').onsubmit = async event => {
  event.preventDefault(); $('message').textContent = '';
  const payload = Object.fromEntries(['name','category','quantity','location'].map(k => [k, $(k).value]));
  try {
    await api(editing === null ? '/api/items' : `/api/items/${editing}`, {
      method: editing === null ? 'POST' : 'PUT', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(payload)
    });
    clear(); await refresh();
  } catch (e) { $('message').textContent = e.message; }
};
$('cancel').onclick = clear;
$('search').oninput = render;
$('export').onclick = () => {
  const fields = ['id','name','category','quantity','location','updated_at'];
  const escape = v => `"${String(v ?? '').replaceAll('"', '""')}"`;
  const csv = '\ufeff' + [fields.join(','), ...rows.map(row => fields.map(k => escape(row[k])).join(','))].join('\r\n');
  const a = document.createElement('a'); a.href = URL.createObjectURL(new Blob([csv], {type:'text/csv'}));
  a.download = 'inventory.csv'; a.click(); setTimeout(() => URL.revokeObjectURL(a.href), 1000);
};
refresh().catch(e => $('message').textContent = e.message);
