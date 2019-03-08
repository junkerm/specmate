/**
 */
package com.specmate.model.administration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Error Code</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.specmate.model.administration.AdministrationPackage#getErrorCode()
 * @model
 * @generated
 */
public enum ErrorCode implements Enumerator {
	/**
	 * The '<em><b>No Such Service</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_SUCH_SERVICE_VALUE
	 * @generated
	 * @ordered
	 */
	NO_SUCH_SERVICE(10, "noSuchService", "noSuchService"),

	/**
	 * The '<em><b>Method Not Allowed</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_NOT_ALLOWED_VALUE
	 * @generated
	 * @ordered
	 */
	METHOD_NOT_ALLOWED(20, "methodNotAllowed", "methodNotAllowed"),

	/**
	 * The '<em><b>In Maintenance Mode</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IN_MAINTENANCE_MODE_VALUE
	 * @generated
	 * @ordered
	 */
	IN_MAINTENANCE_MODE(30, "inMaintenanceMode", "inMaintenanceMode"),

	/**
	 * The '<em><b>Invalid Data</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INVALID_DATA_VALUE
	 * @generated
	 * @ordered
	 */
	INVALID_DATA(40, "invalidData", "invalidData"),

	/**
	 * The '<em><b>Validator</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VALIDATOR_VALUE
	 * @generated
	 * @ordered
	 */
	VALIDATOR(41, "validator", "validator"),

	/**
	 * The '<em><b>No Authorization</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_AUTHORIZATION_VALUE
	 * @generated
	 * @ordered
	 */
	NO_AUTHORIZATION(50, "noAuthorization", "noAuthorization"),

	/**
	 * The '<em><b>Internal Problem</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTERNAL_PROBLEM_VALUE
	 * @generated
	 * @ordered
	 */
	INTERNAL_PROBLEM(100, "internalProblem", "internalProblem"),

	/**
	 * The '<em><b>User Session</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USER_SESSION_VALUE
	 * @generated
	 * @ordered
	 */
	USER_SESSION(101, "userSession", "userSession"),

	/**
	 * The '<em><b>Configuration</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONFIGURATION_VALUE
	 * @generated
	 * @ordered
	 */
	CONFIGURATION(102, "configuration", "configuration"),

	/**
	 * The '<em><b>Persistency</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PERSISTENCY_VALUE
	 * @generated
	 * @ordered
	 */
	PERSISTENCY(103, "persistency", "persistency"),

	/**
	 * The '<em><b>Migration</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MIGRATION_VALUE
	 * @generated
	 * @ordered
	 */
	MIGRATION(104, "migration", "migration"),

	/**
	 * The '<em><b>Seralization</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SERALIZATION_VALUE
	 * @generated
	 * @ordered
	 */
	SERALIZATION(105, "seralization", "seralization"),

	/**
	 * The '<em><b>Rest Service</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REST_SERVICE_VALUE
	 * @generated
	 * @ordered
	 */
	REST_SERVICE(106, "restService", "restService"),

	/**
	 * The '<em><b>Scheduler</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCHEDULER_VALUE
	 * @generated
	 * @ordered
	 */
	SCHEDULER(107, "scheduler", "scheduler"),

	/**
	 * The '<em><b>Hp Proxy</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HP_PROXY_VALUE
	 * @generated
	 * @ordered
	 */
	HP_PROXY(108, "hpProxy", "hpProxy"),

	/**
	 * The '<em><b>Jira</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JIRA_VALUE
	 * @generated
	 * @ordered
	 */
	JIRA(109, "jira", "jira"),

	/**
	 * The '<em><b>Metrics</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METRICS_VALUE
	 * @generated
	 * @ordered
	 */
	METRICS(110, "metrics", "metrics"),

	/**
	 * The '<em><b>Search</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SEARCH_VALUE
	 * @generated
	 * @ordered
	 */
	SEARCH(111, "search", "search"),

	/**
	 * The '<em><b>Testgeneration</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TESTGENERATION_VALUE
	 * @generated
	 * @ordered
	 */
	TESTGENERATION(112, "testgeneration", "testgeneration"),

	/**
	 * The '<em><b>Trello</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRELLO_VALUE
	 * @generated
	 * @ordered
	 */
	TRELLO(113, "trello", "trello"),

	/**
	 * The '<em><b>Nlp</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NLP_VALUE
	 * @generated
	 * @ordered
	 */
	NLP(114, "nlp", "nlp");

	/**
	 * The '<em><b>No Such Service</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>No Such Service</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_SUCH_SERVICE
	 * @model name="noSuchService"
	 * @generated
	 * @ordered
	 */
	public static final int NO_SUCH_SERVICE_VALUE = 10;

	/**
	 * The '<em><b>Method Not Allowed</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Method Not Allowed</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METHOD_NOT_ALLOWED
	 * @model name="methodNotAllowed"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_NOT_ALLOWED_VALUE = 20;

	/**
	 * The '<em><b>In Maintenance Mode</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>In Maintenance Mode</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IN_MAINTENANCE_MODE
	 * @model name="inMaintenanceMode"
	 * @generated
	 * @ordered
	 */
	public static final int IN_MAINTENANCE_MODE_VALUE = 30;

	/**
	 * The '<em><b>Invalid Data</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Invalid Data</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INVALID_DATA
	 * @model name="invalidData"
	 * @generated
	 * @ordered
	 */
	public static final int INVALID_DATA_VALUE = 40;

	/**
	 * The '<em><b>Validator</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Validator</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VALIDATOR
	 * @model name="validator"
	 * @generated
	 * @ordered
	 */
	public static final int VALIDATOR_VALUE = 41;

	/**
	 * The '<em><b>No Authorization</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>No Authorization</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_AUTHORIZATION
	 * @model name="noAuthorization"
	 * @generated
	 * @ordered
	 */
	public static final int NO_AUTHORIZATION_VALUE = 50;

	/**
	 * The '<em><b>Internal Problem</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Internal Problem</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INTERNAL_PROBLEM
	 * @model name="internalProblem"
	 * @generated
	 * @ordered
	 */
	public static final int INTERNAL_PROBLEM_VALUE = 100;

	/**
	 * The '<em><b>User Session</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>User Session</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USER_SESSION
	 * @model name="userSession"
	 * @generated
	 * @ordered
	 */
	public static final int USER_SESSION_VALUE = 101;

	/**
	 * The '<em><b>Configuration</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Configuration</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONFIGURATION
	 * @model name="configuration"
	 * @generated
	 * @ordered
	 */
	public static final int CONFIGURATION_VALUE = 102;

	/**
	 * The '<em><b>Persistency</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Persistency</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PERSISTENCY
	 * @model name="persistency"
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCY_VALUE = 103;

	/**
	 * The '<em><b>Migration</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Migration</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MIGRATION
	 * @model name="migration"
	 * @generated
	 * @ordered
	 */
	public static final int MIGRATION_VALUE = 104;

	/**
	 * The '<em><b>Seralization</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Seralization</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SERALIZATION
	 * @model name="seralization"
	 * @generated
	 * @ordered
	 */
	public static final int SERALIZATION_VALUE = 105;

	/**
	 * The '<em><b>Rest Service</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rest Service</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REST_SERVICE
	 * @model name="restService"
	 * @generated
	 * @ordered
	 */
	public static final int REST_SERVICE_VALUE = 106;

	/**
	 * The '<em><b>Scheduler</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Scheduler</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCHEDULER
	 * @model name="scheduler"
	 * @generated
	 * @ordered
	 */
	public static final int SCHEDULER_VALUE = 107;

	/**
	 * The '<em><b>Hp Proxy</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Hp Proxy</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HP_PROXY
	 * @model name="hpProxy"
	 * @generated
	 * @ordered
	 */
	public static final int HP_PROXY_VALUE = 108;

	/**
	 * The '<em><b>Jira</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Jira</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JIRA
	 * @model name="jira"
	 * @generated
	 * @ordered
	 */
	public static final int JIRA_VALUE = 109;

	/**
	 * The '<em><b>Metrics</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Metrics</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METRICS
	 * @model name="metrics"
	 * @generated
	 * @ordered
	 */
	public static final int METRICS_VALUE = 110;

	/**
	 * The '<em><b>Search</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Search</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SEARCH
	 * @model name="search"
	 * @generated
	 * @ordered
	 */
	public static final int SEARCH_VALUE = 111;

	/**
	 * The '<em><b>Testgeneration</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Testgeneration</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TESTGENERATION
	 * @model name="testgeneration"
	 * @generated
	 * @ordered
	 */
	public static final int TESTGENERATION_VALUE = 112;

	/**
	 * The '<em><b>Trello</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Trello</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TRELLO
	 * @model name="trello"
	 * @generated
	 * @ordered
	 */
	public static final int TRELLO_VALUE = 113;

	/**
	 * The '<em><b>Nlp</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Nlp</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NLP
	 * @model name="nlp"
	 * @generated
	 * @ordered
	 */
	public static final int NLP_VALUE = 114;

	/**
	 * An array of all the '<em><b>Error Code</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ErrorCode[] VALUES_ARRAY =
		new ErrorCode[] {
			NO_SUCH_SERVICE,
			METHOD_NOT_ALLOWED,
			IN_MAINTENANCE_MODE,
			INVALID_DATA,
			VALIDATOR,
			NO_AUTHORIZATION,
			INTERNAL_PROBLEM,
			USER_SESSION,
			CONFIGURATION,
			PERSISTENCY,
			MIGRATION,
			SERALIZATION,
			REST_SERVICE,
			SCHEDULER,
			HP_PROXY,
			JIRA,
			METRICS,
			SEARCH,
			TESTGENERATION,
			TRELLO,
			NLP,
		};

	/**
	 * A public read-only list of all the '<em><b>Error Code</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ErrorCode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Error Code</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ErrorCode get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ErrorCode result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Error Code</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ErrorCode getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ErrorCode result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Error Code</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ErrorCode get(int value) {
		switch (value) {
			case NO_SUCH_SERVICE_VALUE: return NO_SUCH_SERVICE;
			case METHOD_NOT_ALLOWED_VALUE: return METHOD_NOT_ALLOWED;
			case IN_MAINTENANCE_MODE_VALUE: return IN_MAINTENANCE_MODE;
			case INVALID_DATA_VALUE: return INVALID_DATA;
			case VALIDATOR_VALUE: return VALIDATOR;
			case NO_AUTHORIZATION_VALUE: return NO_AUTHORIZATION;
			case INTERNAL_PROBLEM_VALUE: return INTERNAL_PROBLEM;
			case USER_SESSION_VALUE: return USER_SESSION;
			case CONFIGURATION_VALUE: return CONFIGURATION;
			case PERSISTENCY_VALUE: return PERSISTENCY;
			case MIGRATION_VALUE: return MIGRATION;
			case SERALIZATION_VALUE: return SERALIZATION;
			case REST_SERVICE_VALUE: return REST_SERVICE;
			case SCHEDULER_VALUE: return SCHEDULER;
			case HP_PROXY_VALUE: return HP_PROXY;
			case JIRA_VALUE: return JIRA;
			case METRICS_VALUE: return METRICS;
			case SEARCH_VALUE: return SEARCH;
			case TESTGENERATION_VALUE: return TESTGENERATION;
			case TRELLO_VALUE: return TRELLO;
			case NLP_VALUE: return NLP;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ErrorCode(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ErrorCode
