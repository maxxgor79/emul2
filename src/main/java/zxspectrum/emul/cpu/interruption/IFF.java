package zxspectrum.emul.cpu.interruption;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IFF.
 *
 * @author Maxim Gorin
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class IFF {

  @Setter
  @Getter
  protected boolean value;
}
