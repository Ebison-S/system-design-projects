# System Design Projects

A chest of system design projects, where each one takes a real-world system and models it in code.

The focus is on design: identifying the entities, deciding what each one is responsible for, and working out how they fit together.

## Projects

| Project | Description | Language | Status |
| --- | --- | --- | --- |
| [Parking Lot](parking_lot/) | Models floors, spots, vehicles, tickets and payments, with pluggable spot allocation, pricing and payment strategies | Java | In progress |

## Repository Layout

Each project has its own top-level folder and is independent of the others. Inside a project, code is organised by role:

```text
parking_lot/
├── entity/      # Domain models
├── enums/       # Fixed sets of values, such as types and statuses, and their allowed transitions
├── exception/   # Domain-specific exceptions
├── service/     # Application logic that coordinates entities and strategies
└── strategy/    # Interchangeable behaviour, such as spot allocation, pricing and payment
```

Projects are plain Java with no frameworks, so the design stays the focus.

## Running a Project

Each project has a `Main` class in a package named after its folder (for example `parking_lot.Main`), which wires the system together and runs a demo. You need a JDK (11 or later).

Run these commands from the repository root, not from inside the project folder, replacing `<project>` with the folder name (for example `parking_lot`).

**PowerShell (Windows)**

```powershell
javac -d out (Get-ChildItem -Recurse <project> -Filter *.java).FullName
java -cp out <project>.Main
```

**Bash (macOS / Linux / Git Bash)**

```bash
javac -d out $(find <project> -name "*.java")
java -cp out <project>.Main
```

- `javac -d out ...` compiles every `.java` file in the project and writes the `.class` files into `out/`, one folder per package. `javac` doesn't search subfolders itself, so the file list is passed in.
- `java -cp out <project>.Main` runs `Main` with `out/` on the classpath.

`out/` is git-ignored. If you see `ClassNotFoundException`, the compile step either didn't run or ran from a different folder.
