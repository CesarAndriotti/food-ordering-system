from pathlib import Path

from fpdf import FPDF

BASE_DIR = Path(__file__).resolve().parent
md_path = BASE_DIR / "arquitectura-actual-es.md"
pdf_path = BASE_DIR / "arquitectura-actual-es.pdf"

content = md_path.read_text(encoding="utf-8")

pdf = FPDF(format="A4")
pdf.set_auto_page_break(auto=True, margin=15)
pdf.add_page()
pdf.set_font("Helvetica", size=11)

for raw_line in content.splitlines():
    line = raw_line.rstrip()
    if not line:
        pdf.ln(4)
        continue

    if line.startswith("# "):
        pdf.set_font("Helvetica", "B", 16)
        pdf.multi_cell(0, 8, line[2:], wrapmode="CHAR")
        pdf.ln(1)
        pdf.set_font("Helvetica", size=11)
    elif line.startswith("## "):
        pdf.set_font("Helvetica", "B", 13)
        pdf.multi_cell(0, 7, line[3:], wrapmode="CHAR")
        pdf.ln(1)
        pdf.set_font("Helvetica", size=11)
    elif line.startswith("### "):
        pdf.set_font("Helvetica", "B", 12)
        pdf.multi_cell(0, 6, line[4:], wrapmode="CHAR")
        pdf.set_font("Helvetica", size=11)
    elif line.startswith("- "):
        pdf.multi_cell(0, 6, "- " + line[2:], wrapmode="CHAR")
    elif line[:2].isdigit() and line[1] == ".":
        pdf.multi_cell(0, 6, line, wrapmode="CHAR")
    else:
        cleaned = line.replace("`", "")
        pdf.multi_cell(0, 6, cleaned, wrapmode="CHAR")

pdf.output(str(pdf_path))
print(f"PDF generado en: {pdf_path}")
