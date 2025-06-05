class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o banco
        val dao = AppDatabase.getDatabase(this).taskDao()
        val repository = TaskRepository(dao)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskViewModel(repository) as T
            }
        })[TaskViewModel::class.java]

        // Configura o RecyclerView
        adapter = TaskAdapter(
            onDeleteClick = { task ->
                viewModel.delete(task)
            },
            onTaskClick = { task ->
                // Editar tarefa (implemente conforme necessário)
            }
        )

        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@TaskActivity)
            adapter = this@TaskActivity.adapter
        }

        // Observa as mudanças no banco
        viewModel.allTasks.observe(this) { tasks ->
            adapter.setTasks(tasks)
        }

        // Adiciona nova tarefa
        binding.btnAddTask.setOnClickListener {
            val taskText = binding.etTask.text.toString()
            if (taskText.isNotEmpty()) {
                viewModel.insert(Task(title = taskText))
                binding.etTask.text.clear()
            }
        }
    }
}