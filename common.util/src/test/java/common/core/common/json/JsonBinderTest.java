package common.core.common.json;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class JsonBinderTest {

	@Test
	public void toJson() {
		JsonBinder<Obj> binder = JsonBinder.binder(Obj.class);
		Obj obj = new Obj();
		Assert.assertEquals("{}", binder.toJson(obj));
		obj.setLongValue(10L);
		obj.setStringValue("s");
		Assert.assertEquals("{\"longValue\":10,\"stringValue\":\"s\"}", binder.toJson(obj));
	}

	class Obj {
		private Long longValue;
		private BigDecimal bigDecimalValue;
		private Date dateValue;
		private String stringValue;

		public Long getLongValue() {
			return longValue;
		}

		public void setLongValue(Long longValue) {
			this.longValue = longValue;
		}

		public BigDecimal getBigDecimalValue() {
			return bigDecimalValue;
		}

		public void setBigDecimalValue(BigDecimal bigDecimalValue) {
			this.bigDecimalValue = bigDecimalValue;
		}

		public Date getDateValue() {
			return dateValue;
		}

		public void setDateValue(Date dateValue) {
			this.dateValue = dateValue;
		}

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

	}
}
