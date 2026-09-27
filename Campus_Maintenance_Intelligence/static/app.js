const $ = id => document.getElementById(id);
async function api(path, options) {
  const response = await fetch(path, options);
  const data = await response.json();
  if (!response.ok) throw new Error(data.error || 'Request failed');
  return data;
}
function node(tag, text, className) {
  const element = document.createElement(tag);
  element.textContent = text;
  if (className) element.className = className;
  return element;
}
async function refresh() {
  try {
    const [issues, stats] = await Promise.all([api('/api/issues'), api('/api/analytics')]);
    $('stats').replaceChildren(...[['Total',stats.total],['Open',stats.by_status.Open || 0],['In progress',stats.by_status['In Progress'] || 0],['Resolved',stats.by_status.Resolved || 0]].map(([label,value]) => {
      const box = node('div', `${value} ${label}`, 'stat'); return box;
    }));
    for (const [id, values] of [['categories',stats.by_category],['locations',stats.by_location]]) {
      $(id).replaceChildren(...Object.entries(values).sort((a,b)=>b[1]-a[1]).slice(0,5).map(([name,count])=>node('p', `${name}: ${count}`)));
    }
    $('issues').replaceChildren(...issues.map(issue => {
      const card = node('article', '', 'issue');
      card.append(node('h3',issue.title),node('p',issue.description),node('p',`${issue.location} · ${issue.category} · ${new Date(issue.created_at).toLocaleString()}`));
      const select = document.createElement('select');
      for (const status of ['Open','In Progress','Resolved']) {
        const option = node('option',status); option.value=status; select.append(option);
      }
      select.value = issue.status;
      select.setAttribute('aria-label', `Status for ${issue.title}`);
      select.onchange = async () => {
        try { await api(`/api/issues/${encodeURIComponent(issue.id)}`,{method:'PATCH',headers:{'Content-Type':'application/json'},body:JSON.stringify({status:select.value})}); await refresh(); }
        catch (error) { alert(error.message); await refresh(); }
      };
      card.append(select); return card;
    }));
    if (!issues.length) $('issues').append(node('p','No issues yet. Add the first one above.'));
  } catch (error) { $('message').textContent = error.message; }
}
$('issueForm').addEventListener('submit',async event => {
  event.preventDefault();
  const form = event.currentTarget;
  const data = Object.fromEntries(new FormData(form));
  try {
    const result = await api('/api/issues',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(data)});
    $('message').textContent = `Saved under ${result.category}. ` + (result.possible_duplicates.length ? `Possible similar issue: ${result.possible_duplicates[0].title}` : '');
    form.reset(); await refresh();
  } catch (error) { $('message').textContent = error.message; }
});
refresh();
