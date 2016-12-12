package cgt.dop.model;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "lennox_projectdetails")
public class LennoxProjectDetails {

                @Id
                @Column(name = "sno")
                private int sno;
                @Column(name = "testname")
                private String testname;
                @Column(name = "buildNo")
                private int buildNo;
                @Column(name = "testsuite")
                private String testsuite;
                @Column(name = "teststarttime")
                private String teststarttime;
                @Column(name = "testendtime")
                private String testendtime;

                @Column(name = "projectId")
                private String projectId;
                
                @Column(name = "testupdatedname")
                private String testupdatedname;
                
                @Column(name = "testdatetimezone")
                private String testdatetimezone;
                
                @Column(name = "testscenarios")
                private String testscenarios;
                
                @Column(name = "testenvurl")
                private String testenvurl;
                
                public String getTestscenarios() {
					return testscenarios;
				}

				public void setTestscenarios(String testscenarios) {
					this.testscenarios = testscenarios;
				}

				public String getTestenvurl() {
					return testenvurl;
				}

				public void setTestenvurl(String testenvurl) {
					this.testenvurl = testenvurl;
				}

				@Transient
                private String testDuration;

				@Transient
                private String testDurationInHrMinSecFormat;
				
				@Transient
                private String testDate;
				
				 @Transient
	             private String kpiSignalDanger;
				 
				public void setKpiSignalDanger(String kpiSignalDanger) {
					this.kpiSignalDanger = kpiSignalDanger;
				}
			
				public String getKpiSignalDanger() {
					return kpiSignalDanger;
				}
				
				@Transient
	             private String kpiSignalSuccess;
				 
				public void setKpiSignalSuccess(String kpiSignalSuccess) {
					this.kpiSignalSuccess = kpiSignalSuccess;
				}
			
				public String getKpiSignalSuccess() {
					return kpiSignalSuccess;
				}
				

				@Transient
	            private String noOfUsers;

				public void setNoOfUsers(String noOfUsers) {
					this.noOfUsers = noOfUsers;
				}

				public String getNoOfUsers() {
					return noOfUsers;
				}

                public String getTestDuration() {
                	Long testDurationLong = Long.parseLong(testendtime) - Long.parseLong(teststarttime);
                	LocalTime timeDurationOfDay = LocalTime.ofSecondOfDay(testDurationLong/1000);
                	testDuration = timeDurationOfDay.toString();
//                	return testDurInHrMinSecFormat(testDuration);
					return testDuration;
				}
                
				public String getTestDurationInHrMinSecFormat() {
					return testDurationInHrMinSecFormat;
				}

				public void setTestDurationInHrMinSecFormat(String testDurationInHrMinSecFormat) {
					this.testDurationInHrMinSecFormat = testDurationInHrMinSecFormat;
				}

				public String testDurInHrMinSecFormat(String testDurationHHMMSS) {
					StringBuffer sb = new StringBuffer();
					if(!"00".equalsIgnoreCase(testDurationHHMMSS.substring(0, 2)))	{
						sb.append(testDurationHHMMSS.substring(0, 2));
	                	sb.append("Hr ");	
					}
					if(!"00".equalsIgnoreCase(testDurationHHMMSS.substring(3, 5)))	{
						sb.append(testDurationHHMMSS.substring(3, 5));
	                	sb.append("Min ");	
					}
					if(!"00".equalsIgnoreCase(testDurationHHMMSS.substring(6)))	{
						sb.append(testDurationHHMMSS.substring(6));
	                	sb.append("Sec");	
					}
					return sb.toString();
				}

				public String getTestDate() {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					GregorianCalendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(Long.parseLong(teststarttime));
					testDate = sdf.format(calendar.getTime());
					return testDate;
				}


                public int getSno() {
                                return sno;
                }

                public void setSno(int sno) {
                                this.sno = sno;
                }

                public String getTestname() {
                                return testname;
                }

                public void setTestname(String testname) {
                                this.testname = testname;
                }

                public int getBuildNo() {
                                return buildNo;
                }

                public void setBuildNo(int buildNo) {
                                this.buildNo = buildNo;
                }

                public String getTeststarttime() {
                                return teststarttime;
                }

                public void setTeststarttime(String teststarttime) {
                                this.teststarttime = teststarttime;
                }

                public String getTestendtime() {
                                return testendtime;
                }

                public void setTestendtime(String testendtime) {
                                this.testendtime = testendtime;
                }

                public String getProjectId() {
                                return projectId;
                }

                public void setProjectId(String projectId) {
                                this.projectId = projectId;
                }

                public String getTestsuite() {
                                return testsuite;
                }

                public void setTestsuite(String testsuite) {
                                this.testsuite = testsuite;
                }

				public String getTestupdatedname() {
					return testupdatedname;
				}

				public void setTestupdatedname(String testupdatedname) {
					this.testupdatedname = testupdatedname;
				}

				public String getTestdatetimezone() {
					return testdatetimezone;
				}

				public void setTestdatetimezone(String testdatetimezone) {
					this.testdatetimezone = testdatetimezone;
				}

                
}
