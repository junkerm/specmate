package com.specmate.testspecification.internal.generators;

class TaggedBoolean {

	public enum ETag {
		ALL, ANY, AUTO
	}

	public boolean value;
	public ETag tag;

	public TaggedBoolean(Boolean value, ETag tag) {
		this.value = value;
		this.tag = tag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + (value ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaggedBoolean other = (TaggedBoolean) obj;
		if (tag != other.tag)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaggedBoolean [value=" + value + ", tag=" + tag + "]";
	}

}