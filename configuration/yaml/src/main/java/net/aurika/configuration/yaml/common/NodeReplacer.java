package net.aurika.configuration.yaml.common;

import net.aurika.common.snakeyaml.node.NodeUtil;
import net.aurika.configuration.yaml.common.NodeReference.Type;
import org.snakeyaml.engine.v2.common.Anchor;
import org.snakeyaml.engine.v2.nodes.*;

import java.util.*;
import java.util.function.Predicate;

public final class NodeReplacer {

  private final Node node;
  private Node newNode;
  private final Map<String, Node> variables;
  private final Map<String, Anchor> anchors;
  private Predicate<ReplacementDetails> ignorePredicate;
  private boolean changed;
  private boolean replaced;
  private final String path;
  private final boolean isRoot;

  private NodeReplacer(Node node, Map<String, Node> variables, Map<String, Anchor> anchors, String path, boolean isRoot) {
    this.changed = false;
    this.replaced = false;
    this.node = Objects.requireNonNull(node);
    this.variables = variables;
    this.anchors = anchors;
    this.isRoot = isRoot;
    this.path = path;
  }

  public NodeReplacer(Node node, Map<String, Node> variables, Map<String, Anchor> anchors) {
    this(node, variables, anchors, null, true);
  }

  public NodeReplacer replaceAll() {
    boolean emptyVars = this.variables == null || this.variables.isEmpty();
    boolean emptyAnchors = this.anchors == null || this.anchors.isEmpty();
    if (emptyVars && emptyAnchors) {
      return this;
    } else {
      this.checkReplaced(false);
      this.replaceAll0();
      if (this.isRoot && this.newNode != null) {
        throw new IllegalStateException("The root node cannot be replaced as a whole: " + this.node);
      } else {
        this.replaced = true;
        return this;
      }
    }
  }

  private void checkReplaced(boolean shouldlBeReplaced) {
    if (shouldlBeReplaced) {
      if (!this.replaced) {
        throw new IllegalStateException("replaceAll() was not called yet: " + this.path);
      }
    } else if (this.replaced) {
      throw new IllegalStateException("This node replacer was already replaced: " + this.path);
    }
  }

  private String newPath(String newPath) {
    return this.path == null ? newPath : this.path + '.' + newPath;
  }

  public boolean isChanged() {
    this.checkReplaced(true);
    return this.changed;
  }

  public Node getNewNode() {
    return this.newNode;
  }

  public NodeReplacer ignore(Predicate<ReplacementDetails> ignorePredicate) {
    this.checkReplaced(false);
    this.ignorePredicate = ignorePredicate;
    return this;
  }

  public Node getFinalNode() {
    this.checkReplaced(true);
    return this.newNode == null ? this.node : this.newNode;
  }

  private boolean isIgnored(ReplacementDetails replacementDetails) {
    return this.ignorePredicate != null && this.ignorePredicate.test(replacementDetails);
  }

  private Node replaceInner(String fullPath, Node target) {
    NodeReplacer replacement = (new NodeReplacer(target, this.variables, this.anchors, fullPath, false)).ignore(
        this.ignorePredicate).replaceAll();
    if (!this.changed) {
      this.changed = replacement.changed;
    }

    return replacement.getFinalNode();
  }

  private void replaceAll0() {
    String str;
    if (this.anchors != null && !this.anchors.isEmpty()) {
      NodeReference ref = this.node.getReference();
      if (ref != null && ref.getType() == Type.ALIAS) {
        str = ref.getReference().getValue();
        Anchor inheritedAnchor = this.anchors.get(str);
        if (inheritedAnchor != null) {
          this.changed = true;
          this.newNode = inheritedAnchor.getReference().clone();
          this.newNode = this.replaceInner(this.path, this.newNode);
          return;
        }
      }
    }

    Node varNode;
    String newStr;
    switch (this.node.getNodeType()) {
      case SCALAR:
        ScalarNode scalarNode = (ScalarNode) this.node;
        str = scalarNode.getValue();
        if (this.variables != null && !this.variables.isEmpty()) {

          for (Map.Entry<String, Node> stringNodeEntry : this.variables.entrySet()) {
            Map.Entry<String, Node> var = stringNodeEntry;
            varNode = var.getValue();
            String strValue;
            boolean scalar;
            if (varNode instanceof ScalarNode) {
              strValue = ((ScalarNode) varNode).getValue();
              scalar = true;
            } else {
              strValue = var.getKey() + '=' + varNode.toString();
              scalar = false;
            }

            if (str.equals(var.getKey())) {
              this.changed = true;
              this.newNode = varNode.clone();
              scalarNode.setValue(strValue);
              return;
            }

            newStr = str.replace(var.getKey(), strValue);
            if (!str.equals(newStr)) {
              this.changed = true;
              str = newStr;
            }
          }
        }

        if (this.changed) {
          scalarNode.setValue(str);
        }

        return;
      case SEQUENCE:
        SequenceNode seqNode = (SequenceNode) this.node;
        List<Node> newElements = new ArrayList<>(seqNode.getValue().size());
        Iterator var16 = seqNode.getValue().iterator();

        while (true) {
          while (var16.hasNext()) {
            Node element = (Node) var16.next();
            varNode = element;
            if (!this.isIgnored(new ReplacementDetails(this.path, element, false, seqNode))) {
              if (this.variables != null && !this.variables.isEmpty() && element instanceof ScalarNode) {
                newStr = ((ScalarNode) element).getValue();
                Node value = this.variables.get(newStr);
                if (value instanceof SequenceNode mergingValues) {
                  newElements.addAll(mergingValues.getValue().stream().map(NodeUtil::deepCopyNode).toList());
                  this.changed = true;
                  continue;
                }
              }

              varNode = this.replaceInner(this.path, element);
            }

            newElements.add(varNode);
          }

          if (this.changed) {
            seqNode.setValue(newElements);
          }

          return;
        }
      case MAPPING:
        MappingNode map = (MappingNode) this.node;
        LinkedHashMap<String, NodeTuple> newPairs = new LinkedHashMap<>(map.getValue().size());

        ScalarNode completeKey;
        Node competeValue;
        for (Iterator<NodeTuple> var7 = map.getValue().iterator(); var7.hasNext(); newPairs.put(
            completeKey.getValue(), new NodeTuple(completeKey, competeValue))) {
          NodeTuple pair = var7.next();
          String newPath = this.newPath(pair.getKey().getValue());
          completeKey = pair.getKey();
          if (!this.isIgnored(new ReplacementDetails(newPath, pair.getKey(), true, (SequenceNode) null))) {
            competeValue = this.replaceInner(this.path, pair.getKey());
            if (!(competeValue instanceof ScalarNode)) {
              throw new RuntimeException(
                  "Mapping key must be a scalar node at path '" + newPath + "' for " + pair.getKey() + " -> " + competeValue);
            }

            completeKey = (ScalarNode) competeValue;
          }

          competeValue = pair.getValue();
          if (!this.isIgnored(new ReplacementDetails(newPath, pair.getValue(), false, (SequenceNode) null))) {
            competeValue = this.replaceInner(newPath, pair.getValue());
          }
        }

        if (this.changed) {
          map.setPairs(newPairs);
        }

        return;
      default:
        throw new IllegalArgumentException("Unknown node type to replace: " + this.node);
    }
  }


  //TODO

  public static final class ReplacementDetails {

    public final String path;
    public final Node node;
    public final boolean isMapKey;
    public final SequenceNode sequence;

    public ReplacementDetails(String path, Node node, boolean isMapKey, SequenceNode sequence) {
      this.path = path;
      this.node = node;
      this.isMapKey = isMapKey;
      this.sequence = sequence;
    }

  }

}
