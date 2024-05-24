package en.via.sep2_exammaster.shared;

import java.io.Serializable;

/**
 * The Examiners enum represents different types of examiners.
 * Used solely by the Exam class, it holds only 3 values as there are only 3
 * types of examiners: <b>Internal</b>, <b>External</b> and <b>Both</b>.
 */
public enum Examiners implements Serializable {

  /** Represents an internal examiner. */
  Internal,

  /** Represents an external examiner. */
  External,

  /** Represents both internal and external examiners. */
  Both
}

