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
