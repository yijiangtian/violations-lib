package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PROTOLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.Violation;

public class ProtoLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String reportContent) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<List<String>> partsPerLine =
        getLines(reportContent, "\\[([^:]+):(\\d+):(\\d+)\\] (.+)");
    for (final List<String> parts : partsPerLine) {
      final String filename = parts.get(1);
      final Integer line = parseInt(parts.get(2));
      final Integer column = parseInt(parts.get(3));
      final String message = parts.get(4);
      violations.add(
          violationBuilder()
              .setParser(PROTOLINT)
              .setStartLine(line)
              .setColumn(column)
              .setFile(filename)
              .setSeverity(ERROR)
              .setMessage(message)
              .build());
    }
    return violations;
  }
}
