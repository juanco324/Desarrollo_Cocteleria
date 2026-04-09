<template>
  <div class="dashboard">

    <!-- ══════════════ SIDEBAR ══════════════ -->
    <aside class="sidebar">
      <div class="sidebar-brand">Sistema Empresarial</div>

      <div class="sidebar-profile">
        <div class="avatar">👤</div>
        <div class="profile-name">{{ session.nombre }}</div>
        <div class="profile-role">{{ currentMode === 'admin' ? 'Administrador' : 'Empleado' }}</div>
      </div>

      <ul class="nav-list">
        <li class="nav-item">
          <button @click="page = 'buscar'" :class="{ active: page === 'buscar' }">
            <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
            </svg>
            Buscar
          </button>
        </li>
        <li class="nav-item">
          <button @click="page = 'empleados'" :class="{ active: page === 'empleados' }">
            <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <path d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2"/><circle cx="9" cy="7" r="4"/>
              <path d="M23 21v-2a4 4 0 00-3-3.87M16 3.13a4 4 0 010 7.75"/>
            </svg>
            Empleados
          </button>
        </li>
        <li class="nav-item">
          <button @click="page = 'productos'" :class="{ active: page === 'productos' }">
            <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <path d="M6 2L3 6v14a2 2 0 002 2h14a2 2 0 002-2V6l-3-4z"/>
              <line x1="3" y1="6" x2="21" y2="6"/><path d="M16 10a4 4 0 01-8 0"/>
            </svg>
            Productos
          </button>
        </li>
        <li class="nav-item">
          <button @click="page = 'ventas'" :class="{ active: page === 'ventas' }">
            <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/>
              <polyline points="17 6 23 6 23 12"/>
            </svg>
            Ventas
          </button>
        </li>
      </ul>

      <div class="sidebar-footer">
        <div class="mode-label">Modo de Vista</div>
        <div class="mode-toggle">
          <button class="mode-btn" :class="{ active: currentMode === 'admin' }"    @click="currentMode = 'admin'">Admin</button>
          <button class="mode-btn" :class="{ active: currentMode === 'empleado' }" @click="currentMode = 'empleado'">Empleado</button>
        </div>
      </div>
    </aside>

    <!-- ══════════════ MAIN ══════════════ -->
    <main class="main">

      <!-- ── BUSCAR ── -->
      <div v-if="page === 'buscar'" class="page-section">
        <div class="page-header">
          <div class="page-title">
            <h1>Búsqueda</h1>
            <p>Busca empleados o productos en el sistema</p>
          </div>
        </div>
        <div class="search-box-wrap">
          <svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
          <input type="text" class="search-box-input" placeholder="Buscar empleados o productos..."
            v-model="searchQuery" @input="doSearch">
        </div>
        <div>
          <div v-if="!searchQuery" class="empty-state">
            <div class="icon">🔍</div>
            <p>Ingresa un término de búsqueda para comenzar</p>
          </div>
          <div v-else-if="searchResults.length === 0" class="empty-state">
            <div class="icon">😕</div>
            <p>No se encontraron resultados para "<strong>{{ searchQuery }}</strong>"</p>
          </div>
          <div v-else class="search-results">
            <div class="search-result-item" v-for="r in searchResults" :key="r.id"
              @click="page = r.tipo === 'Empleado' ? 'empleados' : 'productos'">
              <div class="result-icon">{{ r.icon }}</div>
              <div>
                <div class="result-name">{{ r.nombre }}</div>
                <div class="result-sub">{{ r.sub }}</div>
              </div>
              <span class="result-type">{{ r.tipo }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- ── EMPLEADOS ── -->
      <div v-if="page === 'empleados'" class="page-section">
        <div class="page-header">
          <div class="page-title">
            <h1>Gestión de Empleados</h1>
            <p>Administra el equipo de trabajo</p>
          </div>
          <button v-if="currentMode === 'admin'" class="btn btn-primary" @click="openEmpModal(null)">
            <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24">
              <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            Nuevo Empleado
          </button>
        </div>

        <div class="stats-row">
          <div class="stat-card">
            <div>
              <div class="stat-label">Total Empleados</div>
              <div class="stat-value">{{ employees.length }}</div>
            </div>
            <div class="stat-icon" style="background:#ebf4ff">👥</div>
          </div>
          <div class="stat-card">
            <div>
              <div class="stat-label">Activos</div>
              <div class="stat-value" style="color:var(--green)">{{ employees.filter(e=>e.estado==='Activo').length }}</div>
            </div>
            <div class="stat-icon" style="background:var(--green-light)">✅</div>
          </div>
          <div class="stat-card">
            <div>
              <div class="stat-label">Nómina Total</div>
              <div class="stat-value" style="color:var(--accent);font-size:20px">
                ${{ employees.filter(e=>e.estado==='Activo').reduce((s,e)=>s+e.salario,0).toLocaleString() }}
              </div>
            </div>
            <div class="stat-icon" style="background:var(--accent-light)">💰</div>
          </div>
        </div>

        <div class="table-card">
          <table>
            <thead>
              <tr>
                <th>Empleado</th><th>Contacto</th><th>Fecha Ingreso</th>
                <th>Salario</th><th>Estado</th>
                <th v-if="currentMode === 'admin'">Acciones</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="e in employees" :key="e.id">
                <td>
                  <div class="emp-name">{{ e.nombre }}</div>
                  <div class="emp-role">{{ e.cargo }}</div>
                </td>
                <td style="font-size:12px;color:var(--text-muted)">✉️ {{ e.email }}</td>
                <td style="font-size:13px">📅 {{ formatDate(e.fecha) }}</td>
                <td><span class="salary">${{ e.salario.toLocaleString() }}</span></td>
                <td>
                  <span class="badge" :class="e.estado === 'Activo' ? 'badge-activo' : 'badge-inactivo'">
                    {{ e.estado }}
                  </span>
                </td>
                <td v-if="currentMode === 'admin'">
                  <div class="action-btns">
                    <button class="icon-btn" @click="openEmpModal(e.id)">
                      <svg width="13" height="13" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                        <path d="M11 4H4a2 2 0 00-2 2v14a2 2 0 002 2h14a2 2 0 002-2v-7"/>
                        <path d="M18.5 2.5a2.121 2.121 0 013 3L12 15l-4 1 1-4 9.5-9.5z"/>
                      </svg>
                    </button>
                    <button class="icon-btn delete" @click="deleteEmployee(e.id)">
                      <svg width="13" height="13" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                        <polyline points="3 6 5 6 21 6"/>
                        <path d="M19 6l-1 14a2 2 0 01-2 2H8a2 2 0 01-2-2L5 6"/>
                      </svg>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- ── PRODUCTOS ── -->
      <div v-if="page === 'productos'" class="page-section">
        <div class="page-header">
          <div class="page-title">
            <h1>Productos</h1>
            <p>Catálogo e inventario de cócteles</p>
          </div>
        </div>

        <div v-if="productosLoading" class="empty-state">
          <div class="icon">⏳</div>
          <p>Cargando productos...</p>
        </div>

        <template v-else>
          <div class="productos-filters">
            <button class="filter-btn" :class="{ active: productoFiltro === '' }" @click="productoFiltro = ''">Todos</button>
            <button class="filter-btn" v-for="cat in categorias" :key="cat"
              :class="{ active: productoFiltro === cat }" @click="productoFiltro = cat">{{ cat }}</button>
          </div>

          <div class="productos-grid">
            <div class="producto-card" v-for="p in productosFiltrados" :key="p.id" :class="{ inactivo: !p.activo }">
              <div class="producto-emoji">{{ p.imagen }}</div>
              <div class="producto-nombre">{{ p.nombre }}</div>
              <div class="producto-categoria">{{ p.categoria }}</div>
              <div class="producto-descripcion">{{ p.descripcion }}</div>
              <div class="producto-footer">
                <span class="producto-precio">${{ p.precio }}</span>
                <span class="badge" :class="p.activo ? 'badge-disponible' : 'badge-inactivo'">
                  {{ p.activo ? 'Disponible' : 'No disponible' }}
                </span>
              </div>
              <div class="producto-stock">Stock: {{ p.stock }} unidades</div>
            </div>
          </div>

          <div v-if="productosFiltrados.length === 0" class="empty-state">
            <div class="icon">🍹</div>
            <p>No hay productos en esta categoría</p>
          </div>
        </template>
      </div>

      <!-- ── VENTAS ── -->
      <div v-if="page === 'ventas'" class="page-section">
        <div class="page-header">
          <div class="page-title">
            <h1>Reporte de Ventas</h1>
            <p>Visualiza todas las ventas y rendimiento del equipo</p>
          </div>
          <button v-if="currentMode === 'admin'" class="btn btn-primary" @click="openVentaModal">
            <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24">
              <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            Nueva Venta
          </button>
        </div>

        <div class="stats-row">
          <div class="stat-card">
            <div>
              <div class="stat-label">Total Ventas</div>
              <div class="stat-value" style="color:var(--accent);font-size:20px">${{ ventaTotalGeneral.toLocaleString() }}</div>
            </div>
            <div class="stat-icon" style="background:var(--accent-light)">💵</div>
          </div>
          <div class="stat-card">
            <div>
              <div class="stat-label">Número de Ventas</div>
              <div class="stat-value" style="color:var(--green)">{{ ventas.length }}</div>
            </div>
            <div class="stat-icon" style="background:var(--green-light)">🛒</div>
          </div>
          <div class="stat-card">
            <div>
              <div class="stat-label">Promedio por Venta</div>
              <div class="stat-value" style="color:#9b59b6;font-size:20px">${{ ventaPromedio.toLocaleString() }}</div>
            </div>
            <div class="stat-icon" style="background:#f9f0ff">📈</div>
          </div>
        </div>

        <div style="margin-bottom:24px">
          <h3 style="font-size:15px;font-weight:700;margin-bottom:14px">Rendimiento por Empleado</h3>
          <div class="perf-grid">
            <div class="perf-card" v-for="(data, idx) in empPerformance" :key="data.empId">
              <div v-if="idx === 0" class="top-badge">⭐ Top</div>
              <div class="perf-emp">
                <div class="perf-avatar">👤</div>
                <div>
                  <div class="perf-name">{{ data.nombre }}</div>
                  <div class="perf-sub">{{ data.count }} ventas</div>
                </div>
              </div>
              <div class="perf-total-label">Total generado</div>
              <div class="perf-total">${{ data.total.toLocaleString() }}</div>
            </div>
          </div>
        </div>

        <div>
          <h3 style="font-size:15px;font-weight:700;margin-bottom:14px">Historial de Todas las Ventas</h3>
          <div class="venta-item" v-for="v in [...ventas].reverse()" :key="v.id">
            <div class="venta-header">
              <div>
                <div class="venta-id">Venta #{{ v.id }}</div>
                <div class="venta-emp">Empleado: {{ nombreEmpleado(v.empleadoId) }}</div>
              </div>
              <div>
                <div class="venta-total">${{ ventaTotal(v).toLocaleString() }}</div>
                <div class="venta-date">{{ formatDateLong(v.fecha) }}</div>
              </div>
            </div>
            <div class="venta-products-title">Productos Vendidos:</div>
            <div class="venta-product-row" v-for="p in v.productos" :key="p.nombre">
              <span>{{ p.nombre }} <span style="color:var(--text-muted)">{{ p.qty }} x ${{ p.precio }}</span></span>
              <span style="font-family:'DM Mono',monospace;font-size:13px">${{ (p.qty * p.precio).toFixed(2) }}</span>
            </div>
          </div>
        </div>
      </div>

    </main>

    <!-- ══ MODAL EMPLEADO ══ -->
    <div class="modal-overlay" :class="{ open: empModal.open }" @click.self="empModal.open = false">
      <div class="modal">
        <div class="modal-header">
          <span class="modal-title">{{ empModal.editId ? 'Editar Empleado' : 'Nuevo Empleado' }}</span>
          <button class="modal-close" @click="empModal.open = false">✕</button>
        </div>
        <div class="form-grid">
          <div class="form-group full">
            <label class="form-label">Nombre Completo</label>
            <input type="text" class="form-input" v-model="empForm.nombre" placeholder="Ej: María González">
          </div>
          <div class="form-group">
            <label class="form-label">Cargo</label>
            <input type="text" class="form-input" v-model="empForm.cargo" placeholder="Ej: Bartender">
          </div>
          <div class="form-group">
            <label class="form-label">Email</label>
            <input type="email" class="form-input" v-model="empForm.email" placeholder="nombre@empresa.com">
          </div>
          <div class="form-group">
            <label class="form-label">Fecha Ingreso</label>
            <input type="date" class="form-input" v-model="empForm.fecha">
          </div>
          <div class="form-group">
            <label class="form-label">Salario ($)</label>
            <input type="number" class="form-input" v-model="empForm.salario" placeholder="0" min="0">
          </div>
          <div class="form-group">
            <label class="form-label">Estado</label>
            <select class="form-select" v-model="empForm.estado">
              <option value="Activo">Activo</option>
              <option value="Inactivo">Inactivo</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-ghost" @click="empModal.open = false">Cancelar</button>
          <button class="btn btn-primary" @click="saveEmployee">Guardar</button>
        </div>
      </div>
    </div>

    <!-- ══ MODAL VENTA ══ -->
    <div class="modal-overlay" :class="{ open: ventaModal.open }" @click.self="ventaModal.open = false">
      <div class="modal">
        <div class="modal-header">
          <span class="modal-title">Nueva Venta</span>
          <button class="modal-close" @click="ventaModal.open = false">✕</button>
        </div>
        <div class="form-grid">
          <div class="form-group full">
            <label class="form-label">Empleado</label>
            <select class="form-select" v-model="ventaForm.empleadoId">
              <option v-for="e in employees.filter(e=>e.estado==='Activo')" :key="e.id" :value="e.id">{{ e.nombre }}</option>
            </select>
          </div>
          <div class="form-group full">
            <label class="form-label">Producto</label>
            <select class="form-select" v-model="ventaForm.productoId" @change="updateVentaPrecio">
              <option v-for="p in productos.filter(p=>p.activo)" :key="p.id" :value="p.id">{{ p.nombre }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">Cantidad</label>
            <input type="number" class="form-input" v-model="ventaForm.cantidad" min="1">
          </div>
          <div class="form-group">
            <label class="form-label">Precio Unitario ($)</label>
            <input type="number" class="form-input" :value="ventaForm.precio" readonly style="background:#f8fafc">
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-ghost" @click="ventaModal.open = false">Cancelar</button>
          <button class="btn btn-primary" @click="saveVenta">Registrar Venta</button>
        </div>
      </div>
    </div>

    <!-- Toast -->
    <div class="toast" :class="{ show: toast.visible }">{{ toast.message }}</div>

  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'

const session     = reactive({ nombre: 'Usuario', email: '', rol: 'empleado' })
const currentMode = ref('admin')
const page        = ref('buscar')

const employees = ref([
  { id:'emp-1', nombre:'María González',  cargo:'Bartender Principal', email:'maria@coctel.com', fecha:'2023-01-14', salario:2500, estado:'Activo' },
  { id:'emp-2', nombre:'Juan Pérez',       cargo:'Asistente de Bar',    email:'juan@coctel.com',  fecha:'2022-06-09', salario:2200, estado:'Activo' },
  { id:'emp-3', nombre:'Ana Rodríguez',    cargo:'Bartender',           email:'ana@coctel.com',   fecha:'2023-08-19', salario:2400, estado:'Activo' },
  { id:'emp-4', nombre:'Luis Martínez',    cargo:'Mesero',              email:'luis@coctel.com',  fecha:'2021-11-04', salario:2300, estado:'Inactivo' }
])

const ventas = ref([
  { id:'venta-1', empleadoId:'emp-1', fecha:'2026-03-19', productos:[{nombre:'Mojito Clásico',qty:4,precio:12},{nombre:'Margarita',qty:2,precio:14}] },
  { id:'venta-2', empleadoId:'emp-1', fecha:'2026-03-18', productos:[{nombre:'Piña Colada',qty:3,precio:13},{nombre:'Negroni',qty:2,precio:16}] },
  { id:'venta-3', empleadoId:'emp-3', fecha:'2026-03-17', productos:[{nombre:'Aperol Spritz',qty:5,precio:11},{nombre:'Cosmopolitan',qty:2,precio:15}] },
  { id:'venta-4', empleadoId:'emp-1', fecha:'2026-03-16', productos:[{nombre:'Old Fashioned',qty:2,precio:18}] },
  { id:'venta-5', empleadoId:'emp-3', fecha:'2026-03-15', productos:[{nombre:'Daiquiri de Fresa',qty:4,precio:13}] }
])

const productos        = ref([])
const productosLoading = ref(true)
const productoFiltro   = ref('')
const searchQuery      = ref('')
const searchResults    = ref([])

const empModal   = reactive({ open: false, editId: null })
const empForm    = reactive({ nombre:'', cargo:'', email:'', fecha:'', salario:0, estado:'Activo' })
const ventaModal = reactive({ open: false })
const ventaForm  = reactive({ empleadoId:'', productoId:'', cantidad:1, precio:0 })
const toast      = reactive({ visible: false, message: '' })

onMounted(async () => {
  try {
    const s = JSON.parse(localStorage.getItem('coctel_session') || '{}')
    if (s.nombre) { session.nombre = s.nombre; session.email = s.email; session.rol = s.rol }
  } catch {}

  try {
    const res = await fetch('/productos.json')
    productos.value = await res.json()
  } catch {
    productos.value = [
      { id:'p1', nombre:'Mojito Clásico', categoria:'Cócteles Clásicos', precio:12, stock:50, descripcion:'Ron blanco, menta, lima y soda', imagen:'🍹', activo:true },
      { id:'p2', nombre:'Margarita',      categoria:'Cócteles Clásicos', precio:14, stock:45, descripcion:'Tequila, triple sec y lima',     imagen:'🍸', activo:true },
      { id:'p3', nombre:'Piña Colada',    categoria:'Tropicales',        precio:13, stock:40, descripcion:'Ron, coco y piña',               imagen:'🥥', activo:true },
      { id:'p4', nombre:'Aperol Spritz',  categoria:'Refrescantes',      precio:11, stock:60, descripcion:'Aperol, prosecco y naranja',     imagen:'🍾', activo:true },
      { id:'p5', nombre:'Negroni',        categoria:'Cócteles Clásicos', precio:16, stock:25, descripcion:'Gin, vermut y Campari',          imagen:'🍊', activo:true }
    ]
  }
  productosLoading.value = false
})

const categorias         = computed(() => [...new Set(productos.value.map(p => p.categoria))])
const productosFiltrados = computed(() =>
  productoFiltro.value ? productos.value.filter(p => p.categoria === productoFiltro.value) : productos.value
)
const ventaTotalGeneral = computed(() => ventas.value.reduce((s, v) => s + ventaTotal(v), 0))
const ventaPromedio     = computed(() => ventas.value.length ? Math.round(ventaTotalGeneral.value / ventas.value.length) : 0)
const empPerformance    = computed(() => {
  const map = {}
  ventas.value.forEach(v => {
    if (!map[v.empleadoId]) map[v.empleadoId] = { count:0, total:0 }
    map[v.empleadoId].count++
    map[v.empleadoId].total += ventaTotal(v)
  })
  return Object.entries(map).sort((a,b) => b[1].total - a[1].total).map(([empId, data]) => {
    const emp = employees.value.find(e => e.id === empId)
    return { empId, nombre: emp ? emp.nombre : 'Desconocido', ...data }
  })
})

function ventaTotal(v)      { return v.productos.reduce((s, p) => s + p.qty * p.precio, 0) }
function nombreEmpleado(id) { const e = employees.value.find(e => e.id === id); return e ? e.nombre : '-' }
function formatDate(d)      { if (!d) return '-'; const [y,m,day]=d.split('-'); return `${parseInt(day)}/${parseInt(m)}/${y}` }
function formatDateLong(d) {
  if (!d) return '-'
  const months = ['enero','febrero','marzo','abril','mayo','junio','julio','agosto','septiembre','octubre','noviembre','diciembre']
  const date = new Date(d + 'T00:00:00')
  return `${date.getDate()} de ${months[date.getMonth()]} de ${date.getFullYear()}`
}
function showToast(msg) { toast.message = msg; toast.visible = true; setTimeout(() => { toast.visible = false }, 2800) }

function doSearch() {
  const q = searchQuery.value.toLowerCase().trim()
  if (!q) { searchResults.value = []; return }
  const empRes  = employees.value.filter(e => e.nombre.toLowerCase().includes(q) || e.cargo.toLowerCase().includes(q)).map(e => ({ id:e.id, tipo:'Empleado', icon:'👤', nombre:e.nombre, sub:`${e.cargo} · ${e.email}` }))
  const prodRes = productos.value.filter(p => p.nombre.toLowerCase().includes(q) || p.categoria.toLowerCase().includes(q)).map(p => ({ id:p.id, tipo:'Producto', icon:p.imagen, nombre:p.nombre, sub:`${p.categoria} · $${p.precio}` }))
  searchResults.value = [...empRes, ...prodRes]
}

function openEmpModal(id) {
  empModal.editId = id
  if (id) { Object.assign(empForm, { ...employees.value.find(x => x.id === id) }) }
  else    { Object.assign(empForm, { nombre:'', cargo:'', email:'', fecha:'', salario:0, estado:'Activo' }) }
  empModal.open = true
}
function saveEmployee() {
  if (!empForm.nombre.trim()) { showToast('⚠️ El nombre es obligatorio'); return }
  const data = { id: empModal.editId || 'emp-'+Date.now(), nombre:empForm.nombre.trim(), cargo:empForm.cargo.trim(), email:empForm.email.trim(), fecha:empForm.fecha, salario:parseFloat(empForm.salario)||0, estado:empForm.estado }
  if (empModal.editId) { const idx = employees.value.findIndex(x => x.id === empModal.editId); employees.value[idx] = data; showToast('✅ Empleado actualizado') }
  else                 { employees.value.push(data); showToast('✅ Empleado agregado') }
  empModal.open = false
}
function deleteEmployee(id) {
  if (!confirm('¿Eliminar este empleado?')) return
  employees.value = employees.value.filter(e => e.id !== id)
  showToast('🗑️ Empleado eliminado')
}

function openVentaModal() {
  ventaForm.empleadoId = employees.value.find(e => e.estado === 'Activo')?.id || ''
  ventaForm.productoId = productos.value.find(p => p.activo)?.id || ''
  ventaForm.cantidad   = 1
  updateVentaPrecio()
  ventaModal.open = true
}
function updateVentaPrecio() { const p = productos.value.find(x => x.id === ventaForm.productoId); ventaForm.precio = p ? p.precio : 0 }
function saveVenta() {
  const prod = productos.value.find(p => p.id === ventaForm.productoId)
  if (!ventaForm.empleadoId || !prod) { showToast('⚠️ Completa todos los campos'); return }
  ventas.value.push({ id:'venta-'+Date.now(), empleadoId:ventaForm.empleadoId, fecha:new Date().toISOString().split('T')[0], productos:[{nombre:prod.nombre, qty:parseInt(ventaForm.cantidad)||1, precio:prod.precio}] })
  ventaModal.open = false
  showToast('✅ Venta registrada')
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=DM+Sans:wght@300;400;500;600;700&family=DM+Mono:wght@400;500&display=swap');

.dashboard {
  display: flex;
  min-height: 100vh;
  background: #f4f6f9;
  font-family: 'DM Sans', sans-serif;
  color: #1a202c;

  --sidebar-w: 210px;
  --surface: #ffffff;
  --border: #e2e8f0;
  --text-muted: #718096;
  --accent: #3b5bdb;
  --accent-light: #eef2ff;
  --accent-hover: #2f4acf;
  --green: #38a169;
  --green-light: #f0fff4;
  --red: #e53e3e;
  --red-light: #fff5f5;
  --shadow: 0 1px 3px rgba(0,0,0,0.08);
  --shadow-md: 0 4px 16px rgba(0,0,0,0.08);
  --radius: 10px;
}

.sidebar {
  width: var(--sidebar-w);
  min-height: 100vh;
  background: var(--surface);
  border-right: 1px solid var(--border);
  display: flex;
  flex-direction: column;
  padding: 24px 14px 20px;
  position: fixed;
  left: 0; top: 0; bottom: 0;
  z-index: 100;
}
.sidebar-brand { font-size: 13px; font-weight: 700; color: var(--accent); letter-spacing: -0.2px; margin-bottom: 20px; padding-left: 4px; }
.sidebar-profile { display: flex; flex-direction: column; align-items: center; padding: 16px 0; border-top: 1px solid var(--border); border-bottom: 1px solid var(--border); margin-bottom: 16px; }
.avatar { width: 60px; height: 60px; background: #e2e8f0; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 28px; margin-bottom: 10px; }
.profile-name { font-size: 13px; font-weight: 700; text-align: center; }
.profile-role { font-size: 11px; color: var(--accent); font-weight: 600; margin-top: 2px; }
.nav-list { list-style: none; display: flex; flex-direction: column; gap: 2px; flex: 1; }
.nav-item button { width: 100%; display: flex; align-items: center; gap: 9px; padding: 9px 10px; border: none; background: none; border-radius: 8px; cursor: pointer; font-family: 'DM Sans', sans-serif; font-size: 13px; font-weight: 500; color: var(--text-muted); transition: all 0.15s; text-align: left; }
.nav-item button:hover { background: #f4f6f9; color: #1a202c; }
.nav-item button.active { background: var(--accent-light); color: var(--accent); font-weight: 600; }
.sidebar-footer { border-top: 1px solid var(--border); padding-top: 16px; }
.mode-label { font-size: 10px; font-weight: 600; text-transform: uppercase; letter-spacing: 0.8px; color: var(--text-muted); margin-bottom: 8px; }
.mode-toggle { display: flex; background: #f4f6f9; border: 1px solid var(--border); border-radius: 8px; overflow: hidden; }
.mode-btn { flex: 1; padding: 7px; font-size: 12px; font-weight: 600; border: none; background: none; cursor: pointer; font-family: 'DM Sans', sans-serif; color: var(--text-muted); transition: all 0.15s; }
.mode-btn.active { background: var(--accent); color: white; }

.main { margin-left: var(--sidebar-w); flex: 1; padding: 32px 36px; min-height: 100vh; }
.page-section { animation: fadeIn 0.2s ease; }
@keyframes fadeIn { from { opacity:0; transform:translateY(6px); } to { opacity:1; transform:translateY(0); } }
.page-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 28px; flex-wrap: wrap; gap: 12px; }
.page-title h1 { font-size: 26px; font-weight: 700; }
.page-title p  { font-size: 13px; color: var(--text-muted); margin-top: 3px; }

.btn { display: inline-flex; align-items: center; gap: 6px; padding: 10px 20px; border-radius: 8px; font-size: 13px; font-weight: 600; cursor: pointer; border: none; transition: all 0.18s; font-family: 'DM Sans', sans-serif; }
.btn-primary { background: var(--accent); color: #fff; }
.btn-primary:hover { background: var(--accent-hover); transform: translateY(-1px); box-shadow: var(--shadow-md); }
.btn-ghost { background: transparent; color: var(--text-muted); border: 1px solid var(--border); }
.btn-ghost:hover { background: #f4f6f9; color: #1a202c; }
.btn-danger { background: var(--red); color: #fff; }
.btn-danger:hover { background: #c53030; }

.stats-row { display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px; margin-bottom: 28px; }
.stat-card { background: var(--surface); border: 1px solid var(--border); border-radius: var(--radius); padding: 18px 22px; display: flex; justify-content: space-between; align-items: center; box-shadow: var(--shadow); }
.stat-label { font-size: 11px; color: var(--text-muted); font-weight: 500; text-transform: uppercase; letter-spacing: 0.5px; }
.stat-value { font-size: 26px; font-weight: 700; margin-top: 5px; }
.stat-icon { width: 42px; height: 42px; border-radius: 10px; display: flex; align-items: center; justify-content: center; font-size: 20px; }

.table-card { background: var(--surface); border: 1px solid var(--border); border-radius: var(--radius); box-shadow: var(--shadow); overflow: hidden; }
table { width: 100%; border-collapse: collapse; }
thead th { padding: 11px 18px; text-align: left; font-size: 11px; font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px; color: var(--text-muted); background: #f8fafc; border-bottom: 1px solid var(--border); }
tbody tr { border-bottom: 1px solid var(--border); transition: background 0.12s; }
tbody tr:last-child { border-bottom: none; }
tbody tr:hover { background: #fafbfc; }
tbody td { padding: 13px 18px; font-size: 13px; vertical-align: middle; }
.emp-name { font-weight: 600; }
.emp-role { font-size: 11px; color: var(--text-muted); margin-top: 1px; }

.badge { display: inline-flex; align-items: center; padding: 4px 12px; border-radius: 20px; font-size: 11px; font-weight: 700; }
.badge-activo    { background: #1a202c; color: white; }
.badge-inactivo  { background: #e2e8f0; color: var(--text-muted); }
.badge-disponible { background: var(--green); color: white; }
.badge-agotado   { background: var(--red-light); color: var(--red); }

.salary { font-family: 'DM Mono', monospace; font-size: 13px; font-weight: 500; }
.action-btns { display: flex; gap: 6px; }
.icon-btn { width: 30px; height: 30px; border-radius: 6px; border: 1px solid var(--border); background: none; cursor: pointer; display: flex; align-items: center; justify-content: center; color: var(--text-muted); transition: all 0.15s; }
.icon-btn:hover { background: var(--accent-light); color: var(--accent); border-color: #c7d2fe; }
.icon-btn.delete:hover { background: var(--red-light); color: var(--red); border-color: #fed7d7; }

.search-box-wrap { background: var(--surface); border: 1px solid var(--border); border-radius: 12px; display: flex; align-items: center; gap: 12px; padding: 14px 18px; margin-bottom: 28px; box-shadow: var(--shadow); }
.search-box-input { flex: 1; border: none; outline: none; font-size: 15px; font-family: 'DM Sans', sans-serif; color: #1a202c; background: transparent; }
.search-box-input::placeholder { color: var(--text-muted); }
.search-results { display: flex; flex-direction: column; gap: 10px; }
.search-result-item { background: var(--surface); border: 1px solid var(--border); border-radius: var(--radius); padding: 14px 18px; display: flex; align-items: center; gap: 14px; box-shadow: var(--shadow); transition: all 0.15s; cursor: pointer; }
.search-result-item:hover { border-color: var(--accent); box-shadow: 0 0 0 3px var(--accent-light); }
.result-icon { font-size: 24px; }
.result-name { font-weight: 600; font-size: 14px; }
.result-sub  { font-size: 12px; color: var(--text-muted); margin-top: 2px; }
.result-type { margin-left: auto; font-family: 'DM Mono', monospace; font-size: 11px; font-weight: 700; padding: 3px 10px; border-radius: 20px; background: var(--accent-light); color: var(--accent); }
.empty-state { text-align: center; padding: 80px 20px; color: var(--text-muted); }
.empty-state .icon { font-size: 52px; margin-bottom: 14px; }
.empty-state p { font-size: 14px; }

.productos-filters { display: flex; gap: 10px; flex-wrap: wrap; margin-bottom: 20px; }
.filter-btn { padding: 6px 16px; border-radius: 20px; font-size: 12px; font-weight: 600; border: 1px solid var(--border); background: var(--surface); cursor: pointer; color: var(--text-muted); transition: all 0.15s; font-family: 'DM Sans', sans-serif; }
.filter-btn:hover { border-color: var(--accent); color: var(--accent); }
.filter-btn.active { background: var(--accent); color: white; border-color: var(--accent); }
.productos-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 16px; }
.producto-card { background: var(--surface); border: 1px solid var(--border); border-radius: var(--radius); padding: 18px; box-shadow: var(--shadow); transition: all 0.15s; display: flex; flex-direction: column; gap: 8px; }
.producto-card:hover { box-shadow: var(--shadow-md); transform: translateY(-2px); }
.producto-card.inactivo { opacity: 0.55; }
.producto-emoji { font-size: 36px; text-align: center; margin-bottom: 4px; }
.producto-nombre { font-weight: 700; font-size: 14px; }
.producto-categoria { font-size: 11px; color: var(--text-muted); }
.producto-descripcion { font-size: 12px; color: var(--text-muted); line-height: 1.4; }
.producto-footer { display: flex; justify-content: space-between; align-items: center; margin-top: 4px; }
.producto-precio { font-family: 'DM Mono', monospace; font-size: 16px; font-weight: 700; color: var(--accent); }
.producto-stock { font-size: 11px; color: var(--text-muted); }

.perf-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 24px; }
.perf-card { background: var(--surface); border: 1px solid var(--border); border-radius: var(--radius); padding: 20px 22px; box-shadow: var(--shadow); position: relative; overflow: hidden; }
.top-badge { position: absolute; top: 14px; right: 14px; background: #fef3c7; color: #92400e; font-size: 11px; font-weight: 700; padding: 3px 10px; border-radius: 20px; }
.perf-emp { display: flex; align-items: center; gap: 10px; margin-bottom: 12px; }
.perf-avatar { width: 36px; height: 36px; background: var(--accent-light); border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 16px; }
.perf-name { font-weight: 700; font-size: 14px; }
.perf-sub  { font-size: 12px; color: var(--text-muted); }
.perf-total-label { font-size: 11px; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; margin-bottom: 4px; }
.perf-total { font-size: 24px; font-weight: 700; color: var(--accent); font-family: 'DM Mono', monospace; }

.venta-item { background: var(--surface); border: 1px solid var(--border); border-radius: var(--radius); padding: 18px 22px; margin-bottom: 12px; box-shadow: var(--shadow); }
.venta-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 14px; }
.venta-id  { font-weight: 700; font-size: 14px; }
.venta-emp { font-size: 12px; color: var(--text-muted); margin-top: 2px; }
.venta-total { font-family: 'DM Mono', monospace; font-size: 16px; font-weight: 700; color: var(--accent); }
.venta-date { font-size: 11px; color: var(--text-muted); text-align: right; margin-top: 2px; }
.venta-products-title { font-size: 10px; font-weight: 700; text-transform: uppercase; letter-spacing: 0.5px; color: var(--text-muted); margin-bottom: 8px; }
.venta-product-row { display: flex; justify-content: space-between; font-size: 13px; padding: 5px 0; border-bottom: 1px dashed var(--border); }
.venta-product-row:last-child { border-bottom: none; }

.modal-overlay { position: fixed; inset: 0; background: rgba(0,0,0,0.4); display: none; align-items: center; justify-content: center; z-index: 1000; opacity: 0; pointer-events: none; transition: opacity 0.2s; padding: 20px; }
.modal-overlay.open { display: flex; opacity: 1; pointer-events: all; }
.modal { background: var(--surface); border-radius: 14px; padding: 28px; width: 100%; max-width: 480px; box-shadow: 0 20px 60px rgba(0,0,0,0.2); transform: translateY(16px); transition: transform 0.2s; }
.modal-overlay.open .modal { transform: translateY(0); }
.modal-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 22px; }
.modal-title { font-size: 16px; font-weight: 700; }
.modal-close { width: 30px; height: 30px; border-radius: 6px; border: 1px solid var(--border); background: none; cursor: pointer; font-size: 16px; color: var(--text-muted); display: flex; align-items: center; justify-content: center; }
.form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; }
.form-group { display: flex; flex-direction: column; gap: 5px; }
.form-group.full { grid-column: 1 / -1; }
.form-label { font-size: 12px; font-weight: 600; color: var(--text-muted); }
.form-input, .form-select { padding: 9px 12px; border: 1px solid var(--border); border-radius: 8px; font-size: 13px; color: #1a202c; background: var(--surface); font-family: 'DM Sans', sans-serif; outline: none; transition: border 0.15s; }
.form-input:focus, .form-select:focus { border-color: var(--accent); box-shadow: 0 0 0 3px var(--accent-light); }
.modal-footer { display: flex; justify-content: flex-end; gap: 10px; margin-top: 22px; }

.toast { position: fixed; bottom: 24px; right: 24px; background: #1a202c; color: white; padding: 12px 18px; border-radius: 8px; font-size: 13px; font-weight: 500; box-shadow: var(--shadow-md); z-index: 9999; transform: translateY(80px); opacity: 0; transition: all 0.3s; pointer-events: none; }
.toast.show { transform: translateY(0); opacity: 1; }
</style>
