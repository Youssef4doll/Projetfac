#!/usr/bin/env python3
"""Insère chaque diagramme (image PNG + source PlantUML repliable) entre les balises
<!-- BEGIN puml:nom --> et <!-- END puml:nom --> des fichiers Markdown de Documentation/. Idempotent."""
import re
from pathlib import Path

DOC = Path(__file__).resolve().parent.parent
SRC = DOC / "Diagrams" / "src"
pattern = re.compile(r"<!-- BEGIN puml:([\w-]+) -->.*?<!-- END puml:\1 -->", re.S)

def block(md_file: Path, name: str) -> str:
    rel = Path("../" * (len(md_file.relative_to(DOC).parts) - 1))
    png = (rel / "Diagrams" / "png" / f"{name}.png").as_posix()
    src = (rel / "Diagrams" / "src" / f"{name}.puml").as_posix()
    code = (SRC / f"{name}.puml").read_text(encoding="utf-8").strip()
    return (f"<!-- BEGIN puml:{name} -->\n![{name}]({png})\n\n"
            f"<details>\n<summary>Code PlantUML — <code>{src}</code></summary>\n\n"
            f"```plantuml\n{code}\n```\n\n</details>\n<!-- END puml:{name} -->")

for md in DOC.rglob("*.md"):
    text = md.read_text(encoding="utf-8")
    new = pattern.sub(lambda m: block(md, m.group(1)), text)
    if new != text:
        md.write_text(new, encoding="utf-8")
        print("mis à jour :", md.relative_to(DOC))
